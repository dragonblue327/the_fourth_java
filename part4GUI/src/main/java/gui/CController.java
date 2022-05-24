package gui;
import java.time.LocalDateTime;
import java.util.*;
import java.awt.image.RenderedImage;

import javax.imageio.ImageIO;
import javax.print.Doc;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;;

import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.nio.file.*;
import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.FileChooser;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import Geometry.IShape;
import Geometry.Point2D;
import Geometry.Segment;
import Geometry.Circle;
import Geometry.Polyline;
import Geometry.NGon;
import Geometry.TGon;
import Geometry.QCon;
import Geometry.Rectangle;
import Geometry.Trapeze;
import lombok.extern.java.Log;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
@Log
public class CController 
{
    @FXML
    // The reference of inputText will be injected by the FXML loader
    private Canvas canvas;

	// square/length/intersection
    @FXML
	private Label lbTextProp1;

	// true/false
    @FXML
	private Label lbTextProp2;

    @FXML
	private Label lbNumProp1;
     
    static List<IShape> figures;

	static double numValue1;
	static String textValue1;
	static String textValue2;

	static int[] markedFigureIndexes;

    private Random rnd;
     
    @FXML
    private void initialize() 
    {
		figures = new ArrayList<IShape>();
    	rnd = new Random();
		updateCanvas();
    }

		static void showAlert(String message, String title,AlertType type) {
			Alert alert = new Alert(type); // AlertType.WARNING/ERROR
			alert.setTitle(title);

			// Header Text: null
			alert.setHeaderText(null);
			alert.setContentText(message);

			alert.showAndWait();
		}

	@FXML
    void addFigure() throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "src/main/addFigure.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
        
        Pane root = (Pane) loader.load(fxmlStream);
        
        Scene scene = new Scene(root);
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.setTitle("Добавление фигуры");
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
						{
								@Override
								public void handle(WindowEvent event)
								{
										showAlert("Finished adding figures","info",AlertType.INFORMATION);
								}
						});
        stage.showAndWait();

		updateCanvas();
    }

	@FXML
    void moveFigure() throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "src/main/moveFigure.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
        
        Pane root = (Pane) loader.load(fxmlStream);
        
        Scene scene = new Scene(root);
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.setTitle("Move figure");
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
						{
								@Override
								public void handle(WindowEvent event)
								{
										showAlert("Finished moving figures","info",AlertType.INFORMATION);
								}
						});
        stage.showAndWait();

		updateCanvas();
    }

	@FXML
    void removeFigure() throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "src/main/removeFigure.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
        
        Pane root = (Pane) loader.load(fxmlStream);
        
        Scene scene = new Scene(root);
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.setTitle("Remove figure");
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
						{
								@Override
								public void handle(WindowEvent event)
								{
										showAlert("Finished removing figures","info",AlertType.INFORMATION);
								}
						});
        stage.showAndWait();

		updateCanvas();
    }

	private void updateCanvas()
	{
		GraphicsContext gc = canvas.getGraphicsContext2D();
		int w = (int)canvas.getWidth();
		int h = (int)canvas.getHeight();
		gc.clearRect(0,0,w,h);

		gc.setStroke(Color.GRAY);
    	gc.strokeLine(w/2, 0, w/2, h);
    	gc.strokeLine(0, h/2, w, h/2);

		gc.setStroke(Color.BLACK);
		for(int i = 0; i < figures.size(); ++i)
		{
				figures.get(i).symAxis(0);
				figures.get(i).shift(new Point2D(w/2,h/2));
				figures.get(i).draw(gc);
				figures.get(i).shift(new Point2D(-w/2,-h/2));
				figures.get(i).symAxis(0);
		}

		if (markedFigureIndexes != null)
		{
				gc.setStroke(Color.RED);
				for(int i = 0; i < markedFigureIndexes.length; ++ i)
				{
						figures.get(markedFigureIndexes[i]).symAxis(0);
						figures.get(markedFigureIndexes[i]).shift(new Point2D(w/2,h/2));
						figures.get(markedFigureIndexes[i]).draw(gc);
						figures.get(markedFigureIndexes[i]).shift(new Point2D(-w/2,-h/2));
						figures.get(markedFigureIndexes[i]).symAxis(0);
				}

				markedFigureIndexes = null;
		}

		if (textValue1 != null)
		{
				lbTextProp1.setText(textValue1);

				if (textValue2 != null)
						lbTextProp2.setText(textValue2);
				else
						lbNumProp1.setText(String.valueOf(numValue1));

				textValue1 = null;
				textValue2 = null;
				numValue1 = 0;
		}
		else
		{
				lbTextProp1.setText("");
				lbTextProp2.setText("");
				lbNumProp1.setText("");
		}
	}



	private static String figuresToString()
	{
		String res = new String();	

		for (int i = 0; i < figures.size(); ++i)
		{
				res+= figures.get(i).curtForm() + "\n";		
		}
		return res;
	}

	private static IShape parseLine(String line) throws Exception
	{

		String[] tks = line.split(";");
		int typeFigure = Integer.parseInt(tks[1]);

		switch (typeFigure)
		{
				case 1:
				{
						double x1 = Double.parseDouble(tks[3]);
						double y1 = Double.parseDouble(tks[4]);
						double x2 = Double.parseDouble(tks[6]);
						double y2 = Double.parseDouble(tks[7]);
						return new Segment(new Point2D(x1,y1), new Point2D(x2,y2));
				}

				case 2:
				{
						double x = Double.parseDouble(tks[3]);
						double y = Double.parseDouble(tks[4]);
						double r = Double.parseDouble(tks[6]);
						return new Circle(new Point2D(x,y),r);
				}

				case 3:
				{
						int n = Integer.parseInt(tks[3]);
						Point2D[] p = new Point2D[n];

						for(int i = 0, j = 5; i < n; ++i, j+=3)
						{
								p[i] = new Point2D(Double.parseDouble(tks[j]),Double.parseDouble(tks[j+1]));

						}
						return new Polyline(p);
				}

				case 4:
				{
						int n = Integer.parseInt(tks[3]);
						Point2D[] p = new Point2D[n];

						for(int i = 0, j = 5; i < n; ++i, j+=3)
						{
								p[i] = new Point2D(Double.parseDouble(tks[j]),Double.parseDouble(tks[j+1]));

						}
						return new NGon(p);
				}

				case 5:
				{
						Point2D[] p = new Point2D[3];

						for(int i = 0, j = 3; i < 3; ++i, j+=3)
						{
								p[i] = new Point2D(Double.parseDouble(tks[j]),Double.parseDouble(tks[j+1]));

						}
						return new TGon(p);
				}

				case 6:
				{
						Point2D[] p = new Point2D[4];

						for(int i = 0, j = 3; i < 4; ++i, j+=3)
						{
								p[i] = new Point2D(Double.parseDouble(tks[j]),Double.parseDouble(tks[j+1]));

						}
						return new QCon(p);
				}

				case 7:
				{
						Point2D[] p = new Point2D[4];

						for(int i = 0, j = 3; i < 4; ++i, j+=3)
						{
								p[i] = new Point2D(Double.parseDouble(tks[j]),Double.parseDouble(tks[j+1]));

						}
						return new Rectangle(p);
				}

				case 8:
				{
						Point2D[] p = new Point2D[4];

						for(int i = 0, j = 3; i < 4; ++i, j+=3)
						{
								p[i] = new Point2D(Double.parseDouble(tks[j]),Double.parseDouble(tks[j+1]));

						}
						return new Trapeze(p);
				}

				default:
						throw new Exception("bad input");
		}

	}

	@FXML
	private void saveToFile()
	{
			try
			{
					Stage stage = new Stage();

					FileChooser fCh = new FileChooser();
					fCh.setTitle("save file");
					File file = fCh.showSaveDialog(stage);

					if( file == null)
							throw new Exception("File hasn't been choosen");

					byte data[] = figuresToString().getBytes();

					OutputStream out = new BufferedOutputStream(
									Files.newOutputStream(file.toPath()));

					out.write(data,0,data.length);
					out.flush();
					out.close();

					showAlert("file was rewritten","info",AlertType.INFORMATION);
					log.info("figures were saved to file: "+file);

			}
			catch(IOException e)
			{
					showAlert("bad file","err",AlertType.ERROR);
			}

			catch(Exception e)
			{
					showAlert(e.getMessage(),"err",AlertType.ERROR);
			}
	}

	@FXML
	private void loadFromFile()
	{
			figures.clear();
			try
			{
					Stage stage = new Stage();

					FileChooser fCh = new FileChooser();
					fCh.setTitle("load file");
					File file = fCh.showOpenDialog(stage);

					if( file == null)
							throw new Exception("File hasn't been choosen");

					InputStream in = Files.newInputStream(file.toPath());
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					String line = null;
					while((line = reader.readLine()) !=null)
					{
						    figures.add(parseLine(line));	
					}


					updateCanvas();
					showAlert("figures loaded","info",AlertType.INFORMATION);
					log.info("figures were loaded from file: "+file);
			}
			catch(IOException e)
			{
					showAlert("bad file","err",AlertType.ERROR);
			}

			catch(Exception e)
			{
					showAlert(e.getMessage(),"err",AlertType.ERROR);
			}
	}

    @FXML
    private void saveImage() 
    {
		Stage stage = new Stage();
		FileChooser savefile = new FileChooser();
		savefile.setTitle("save image");

		File file = savefile.showSaveDialog(stage);
		
		if(file == null)
		{
				showAlert("File hasn't been choosen","err",AlertType.ERROR);
				return;
		}

		try
		{
//				SnapshotParameters sp = new SnapshotParameters();
//				sp.setFill(Color.TRANSPARENT);
				WritableImage writableImage = new WritableImage((int)(canvas.getHeight()), (int)(canvas.getWidth()));
				canvas.snapshot(null,writableImage);
				RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage,null);
				ImageIO.write(renderedImage, "png", file);

				showAlert("Image was saved","info",AlertType.INFORMATION);
		}
		catch(Exception e)
		{
				showAlert(e.getMessage(),"err",AlertType.ERROR);
		}
    }
    
    @FXML
	private void clearCanvas()
	{
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Clear canvas");
      alert.setHeaderText("Are you sure want to clear canvas");
      alert.setContentText("");

      // option != null.
      Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) 
	  {
		showAlert("Clearing canceled","info",AlertType.INFORMATION);
      }
	  else if (option.get() == ButtonType.OK)
	  {
		figures.clear();
		updateCanvas();
		showAlert("Canvas is clear now","info",AlertType.INFORMATION);
		log.info("All figures were removed");
      }
	  else if (option.get() == ButtonType.CANCEL) 
	  {
		showAlert("Clearing canceled","info",AlertType.INFORMATION);
      }
	  else
	  {
		showAlert("Clearing canceled","info",AlertType.INFORMATION);
      }
	}
    
	@FXML
    void findLength() throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "src/main/findLength.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
        
        Pane root = (Pane) loader.load(fxmlStream);
        
        Scene scene = new Scene(root);
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.setTitle("Find length");
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
						{
								@Override
								public void handle(WindowEvent event)
								{
										showAlert("Finished mesuarements","info",AlertType.INFORMATION);
								}
						});
        stage.showAndWait();

		updateCanvas();
    }

	@FXML
    void findSquare() throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "src/main/findSquare.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
        
        Pane root = (Pane) loader.load(fxmlStream);
        
        Scene scene = new Scene(root);
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.setTitle("Find square");
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
						{
								@Override
								public void handle(WindowEvent event)
								{
										showAlert("Finished mesuarements","info",AlertType.INFORMATION);
								}
						});
        stage.showAndWait();

		updateCanvas();
    }

	@FXML
    void findIntersection() throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "src/main/findIntersection.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
        
        Pane root = (Pane) loader.load(fxmlStream);
        
        Scene scene = new Scene(root);
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.setTitle("Find intersection");
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
						{
								@Override
								public void handle(WindowEvent event)
								{
										showAlert("Finished mesuarements","info",AlertType.INFORMATION);
								}
						});
        stage.showAndWait();

		updateCanvas();
    }

	@FXML
	public void saveToDB()
	{
		try {
			String ipAdress = null;
			if (ipAdress == null)
				ipAdress = "mongodb://localhost:27017";
			MongoClient mongoClient = MongoClients.create(ipAdress);
			CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries
					(MongoClientSettings.getDefaultCodecRegistry(),org.bson.codecs.configuration.CodecRegistries.fromProviders
							(PojoCodecProvider.builder().automatic(true).build()));
			MongoDatabase database = mongoClient.getDatabase("myMongoDb").withCodecRegistry(pojoCodecRegistry);
			MongoCollection<Document> figureCollection = database.getCollection("figures");
			figureCollection.drop();

			database.createCollection("figures");

			figureCollection = database.getCollection("figures");
			List<Document> bsonFigures = new ArrayList<Document>();
			for (int i = 0; i < figures.size(); ++i)
				bsonFigures.add(figures.get(i).toBson());
			figureCollection.insertMany(bsonFigures);
			showAlert("Figures were saved to DB", "info", AlertType.INFORMATION);
			log.info("figures were saved to DB "+figures.size()+" " + bsonFigures.size()+" "+figureCollection.countDocuments());
		}
		catch (Exception e)
		{
			showAlert("Failed to connect DB", "error", AlertType.ERROR);
			log.info(e.getMessage());
		}
	}

	public void addBsonToFigureList(List<Document> bsonFigures) throws Exception
	{
		for(int i = 0; i < bsonFigures.size(); ++i) {
			Document doc = bsonFigures.get(i);
			figures.add(parseLine((String)doc.get("data")));
		}
	}
	@FXML
	public void loadFromDB()
	{
		try {
			String ipAdress = null;
			if (ipAdress == null)
				ipAdress = "mongodb://localhost:27017";
			MongoClient mongoClient = MongoClients.create(ipAdress);
			CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries
					(MongoClientSettings.getDefaultCodecRegistry(),org.bson.codecs.configuration.CodecRegistries.fromProviders
							(PojoCodecProvider.builder().automatic(true).build()));
			MongoDatabase database = mongoClient.getDatabase("myMongoDb").withCodecRegistry(pojoCodecRegistry);
			MongoCollection<Document> figureCollection = database.getCollection("figures");

			List<Document> bsonFigures = new ArrayList<>();
			MongoCursor cursor = figureCollection.find().cursor();
			while (cursor.hasNext()) {
				bsonFigures.add((Document) cursor.next());
			}

			figures.clear();
			addBsonToFigureList(bsonFigures);
			updateCanvas();
			showAlert("Figures were loaded from DB", "info", AlertType.INFORMATION);
			log.info("figures were loaded from DB");
		}
		catch(Exception e)
		{
			showAlert("Failed to connect DB", "error", AlertType.ERROR);
		}
	}


}
