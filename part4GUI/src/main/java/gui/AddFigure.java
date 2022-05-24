package gui;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXML;

import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.geometry.HPos;
import javafx.geometry.VPos;

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

@Log
public class AddFigure
{
		@FXML	
		private Label labFigureName;

		@FXML	
		private Button bCancel;

		@FXML	
		private Spinner<Integer> pointNumSpinner;

		@FXML	
		private GridPane coordinatesPane;

		private int currentFigure;

		private int countPoints;

		@FXML
		private void initialize() 
		{
		    //pointNumSpinner.setEditable(true);
			SpinnerValueFactory.IntegerSpinnerValueFactory intFactory =	new SpinnerValueFactory.IntegerSpinnerValueFactory(3,10,1);
//			{
//				@Override
//				public void decrement(int steps)
//				{
//						countPoints-=1;
//						System.out.println(countPoints);
//				}
//
//				@Override
//				public void increment(int steps)
//				{
//						countPoints+=1;
//						System.out.println(countPoints);
//				}
//			};
//				(SpinnerValueFactory.IntegerSpinnerValueFactory) pointNumSpinner.getValueFactory();
//			intFactory.setMin(3);
//			intFactory.setMax(10);
//			intFactory.setAmountToStepBy(1);
		    currentFigure = 0;
			countPoints = 3;
			pointNumSpinner.setValueFactory(intFactory);

			pointNumSpinner.valueProperty().addListener(new ChangeListener<Integer>()
							{
								@Override
								public void changed(ObservableValue<? extends Integer> obserable, Integer oldVal, Integer newVal)
								{
										countPoints = newVal;
										updatePointFieildsCount();
								}
							});
		}

		private void updatePointFieildsCount(int count)
		{
				coordinatesPane.getChildren().clear();
				for (int i = 0; i < count; ++i)
				{
						Label lx = new Label("x"); 
						Label ly = new Label("y"); 
				
						TextField fx = new TextField();
						fx.setPrefWidth(50);
						TextField fy = new TextField();
						fy.setPrefWidth(50);

						coordinatesPane.setHalignment(lx, HPos.CENTER);
						coordinatesPane.setHalignment(fx, HPos.CENTER);
						coordinatesPane.setHalignment(ly, HPos.CENTER);
						coordinatesPane.setHalignment(fy, HPos.CENTER);

						coordinatesPane.add(lx,0,i);
						coordinatesPane.add(fx,1,i);
						coordinatesPane.add(ly,2,i);
						coordinatesPane.add(fy,3,i);
				}
		}

		private void updatePointFieildsCount()
		{
				updatePointFieildsCount(countPoints);
		}

		@FXML
		private void caseSegment()
		{
				currentFigure = 1;
				labFigureName.setText("Segment");
				pointNumSpinner.setDisable(true);
				coordinatesPane.getChildren().clear();
				updatePointFieildsCount(2);
		}

		@FXML
		private void caseCircle()
		{
				currentFigure = 2;
				labFigureName.setText("Circle");
				pointNumSpinner.setDisable(true);
				coordinatesPane.getChildren().clear();
				updatePointFieildsCount(1);

				Label lr = new Label("radius"); 
				TextField fr = new TextField();
				fr.setPrefWidth(50);

				coordinatesPane.setHalignment(lr, HPos.CENTER);
				coordinatesPane.setHalignment(fr, HPos.CENTER);

				coordinatesPane.add(lr,0,1);
				coordinatesPane.add(fr,1,1);
		}

		@FXML
		private void casePolyline()
		{
				currentFigure = 3;
				labFigureName.setText("Polyline");
				pointNumSpinner.setDisable(false);
				updatePointFieildsCount();

		}

		@FXML
		private void caseNGon()
		{
				currentFigure = 4;
				labFigureName.setText("NGon");
				pointNumSpinner.setDisable(false);
				updatePointFieildsCount();
		}

		@FXML
		private void caseTGon()
		{
				currentFigure = 5;
				labFigureName.setText("TGon");
				pointNumSpinner.setDisable(true);
				updatePointFieildsCount(3);
		}

		@FXML
		private void caseQGon()
		{
				currentFigure = 6;
				labFigureName.setText("QGon");
				pointNumSpinner.setDisable(true);
				updatePointFieildsCount(4);
		}

		@FXML
		private void caseRectangle()
		{
				currentFigure = 7;
				labFigureName.setText("Rectangle");
				pointNumSpinner.setDisable(true);
				updatePointFieildsCount(4);
		}

		@FXML
		private void caseTrapeze()
		{
				currentFigure = 8;
				labFigureName.setText("Trapeze");
				pointNumSpinner.setDisable(true);
				updatePointFieildsCount(4);
		}


		@FXML
		private void confirm()
		{

				try
				{
						switch (currentFigure)
						{
								case 0:
										CController.showAlert("Figure hadn't choosen","err",AlertType.ERROR);
										return;
								case 1:
										addSegment();
										break;
								case 2:
										addCircle();
										break;
								case 3:
										addPolyline();
										break;
								case 4:
										addNGon();
										break;
								case 5:
										addTGon();
										break;
								case 6:
										addQGon();
										break;
								case 7:
										addRectangle();
										break;
								case 8:
										addTrapeze();
										break;
								default:
										break;
						}

						CController.showAlert("Figure was added to list","Success!",AlertType.INFORMATION);
						log.info("Figure was added to list:\n"+CController.figures.get(CController.figures.size()-1).toString());
				}
				catch (NumberFormatException e)
				{
						CController.showAlert("Some fields are not filled or filled with non-numeric data","err",AlertType.ERROR);
				}

				catch(Exception e)
				{
						CController.showAlert(e.getMessage(),"err",AlertType.ERROR);
				}
		}

		private void addSegment()throws Exception
		{
				ObservableList<Node> nodes = coordinatesPane.getChildren();

				Point2D[] p = new Point2D[2];
				for(int i =0; i < 2; ++i)
				{
						p[i] = new Point2D();
						for(int j =0; j < 2; ++j)
						{
								String val = ((TextField)nodes.get(i * 4 + j*2 + 1)).getText();		
								p[i].setX(Double.parseDouble(val),j);	
						}
				}

				CController.figures.add(new Segment(p[0],p[1]));
		}

		private void addCircle()throws Exception
		{
				ObservableList<Node> nodes = coordinatesPane.getChildren();

				Point2D p = new Point2D();
				for(int j =0; j < 2; ++j)
				{
						String val = ((TextField)nodes.get(j*2 + 1)).getText();		
						p.setX(Double.parseDouble(val),j);	
				}
				double r = Double.parseDouble(((TextField)nodes.get(5)).getText());
				

				CController.figures.add(new Circle(p,r));
		}

		private void addPolyline()throws Exception
		{
				ObservableList<Node> nodes = coordinatesPane.getChildren();

				Point2D[] p = new Point2D[countPoints];
				for(int i =0; i < countPoints; ++i)
				{
						p[i] = new Point2D();
						for(int j =0; j < 2; ++j)
						{
								String val = ((TextField)nodes.get(i * 4 + j*2 + 1)).getText();		
								p[i].setX(Double.parseDouble(val),j);	
						}
				}

				CController.figures.add(new Polyline(p));
		}


		private void addNGon()throws Exception
		{
				ObservableList<Node> nodes = coordinatesPane.getChildren();

				Point2D[] p = new Point2D[countPoints];
				for(int i =0; i < countPoints; ++i)
				{
						p[i] = new Point2D();
						for(int j =0; j < 2; ++j)
						{
								String val = ((TextField)nodes.get(i * 4 + j*2 + 1)).getText();		
								p[i].setX(Double.parseDouble(val),j);	
						}
				}

				CController.figures.add(new NGon(p));
		}

		private void addTGon()throws Exception
		{
				ObservableList<Node> nodes = coordinatesPane.getChildren();

				Point2D[] p = new Point2D[3];
				for(int i =0; i < 3; ++i)
				{
						p[i] = new Point2D();
						for(int j =0; j < 2; ++j)
						{
								String val = ((TextField)nodes.get(i * 4 + j*2 + 1)).getText();		
								p[i].setX(Double.parseDouble(val),j);	
						}
				}

				CController.figures.add(new TGon(p));
		}

		private void addQGon()throws Exception
		{
				ObservableList<Node> nodes = coordinatesPane.getChildren();

				Point2D[] p = new Point2D[4];
				for(int i =0; i < 4; ++i)
				{
						p[i] = new Point2D();
						for(int j =0; j < 2; ++j)
						{
								String val = ((TextField)nodes.get(i * 4 + j*2 + 1)).getText();		
								p[i].setX(Double.parseDouble(val),j);	
						}
				}

				CController.figures.add(new QCon(p));
		}

		private void addRectangle()throws Exception
		{
				ObservableList<Node> nodes = coordinatesPane.getChildren();

				Point2D[] p = new Point2D[4];
				for(int i =0; i < 4; ++i)
				{
						p[i] = new Point2D();
						for(int j =0; j < 2; ++j)
						{
								String val = ((TextField)nodes.get(i * 4 + j*2 + 1)).getText();		
								p[i].setX(Double.parseDouble(val),j);	
						}
				}

				CController.figures.add(new Rectangle(p));
		}

		private void addTrapeze() throws Exception
		{
				ObservableList<Node> nodes = coordinatesPane.getChildren();

				Point2D[] p = new Point2D[4];
				for(int i =0; i < 4; ++i)
				{
						p[i] = new Point2D();
						for(int j =0; j < 2; ++j)
						{
								String val = ((TextField)nodes.get(i * 4 + j*2 + 1)).getText();		
								p[i].setX(Double.parseDouble(val),j);	
						}
				}

				CController.figures.add(new Trapeze(p));
		}

		@FXML
		private void cancel()
		{
				CController.showAlert("Finished adding figures","info",AlertType.INFORMATION);
				Stage stage = (Stage) bCancel.getScene().getWindow(); 
				stage.close();
		}
}
