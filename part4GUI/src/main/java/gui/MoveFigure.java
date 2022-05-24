package gui;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXML;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.geometry.HPos;
import javafx.geometry.VPos;

import Geometry.IMoveable;
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

@Log
public class MoveFigure
{

		class ObservableOption
		{
				public ObservableOption(int i, String s)
				{
						this.i = i;
						this.s = s;
				}
				public int i;
				public String s;
				public String toString()
				{
						return s;
				}
		}

		private int countFigures;
		private int typeTransformation;

		@FXML	
		private GridPane transformConf;

		@FXML
		private Button bCancel;

		@FXML
		private ComboBox cbFigures;

		@FXML
		private ComboBox cbMoveType;

		@FXML
		private void initialize() throws Exception
		{
				typeTransformation = 0;

				countFigures = CController.figures.size();
				ObservableList<ObservableOption> figureNames = FXCollections.observableArrayList();
				for(int i = 0; i < countFigures; ++i)
				{
						figureNames.add(new ObservableOption(i,CController.figures.get(i).toString()));
				}

				cbFigures.setItems(figureNames);

				ObservableOption opt1 =  new ObservableOption(1,"Translate");
				ObservableOption opt2 =  new ObservableOption(2,"Rotate");
				ObservableOption opt3 =  new ObservableOption(3,"Axis symetry");

				ObservableList<ObservableOption> moveTypeOptions = FXCollections.observableArrayList	
						(opt1,opt2,opt3);

				cbMoveType.setItems(moveTypeOptions);
				cbMoveType.valueProperty().addListener(new ChangeListener<ObservableOption>()
								{
									@Override
									public void changed(ObservableValue observable, ObservableOption oldVal, ObservableOption newVal)
									{
											typeTransformation = newVal.i;
											updateTransformPropertiesFields();
									}
								});
		}
		
		private void updateTransformPropertiesFields()
		{
				transformConf.getChildren().clear();
				switch (typeTransformation)
				{
						case 1:
								{
								Label lx = new Label("x"); 
								Label ly = new Label("y"); 
						
								TextField fx = new TextField();
								fx.setPrefWidth(50);
								TextField fy = new TextField();
								fy.setPrefWidth(50);

								transformConf.setHalignment(lx, HPos.CENTER);
								transformConf.setHalignment(fx, HPos.CENTER);
								transformConf.setHalignment(ly, HPos.CENTER);
								transformConf.setHalignment(fy, HPos.CENTER);

								transformConf.add(lx,0,0);
								transformConf.add(fx,1,0);
								transformConf.add(ly,2,0);
								transformConf.add(fy,3,0);
								break;
								}
						case 2:
								{
								Label ldg = new Label("Degrees"); 
						
								TextField fdg = new TextField();
								fdg.setPrefWidth(50);
								transformConf.add(ldg,0,0);
								transformConf.add(fdg,1,0);
								break;
								}
						case 3:
								{
								Label ldg = new Label("Axis"); 

								ToggleGroup group = new ToggleGroup();	
								//group.setPrefWidth(50);

								RadioButton bx = new RadioButton("x");
								RadioButton by = new RadioButton("y");
								bx.setToggleGroup(group);
								bx.setSelected(true);
								by.setToggleGroup(group);

								transformConf.add(ldg,0,0);
								transformConf.add(bx,1,0);
								transformConf.add(by,2,0);
								break;
								}
						default:
								break;
				}

		}

		@FXML
		private void change()
		{
				try
				{
						ObservableOption fig = (ObservableOption)cbFigures.getValue();	
						if (fig == null)
								throw (new Exception("Figured hasn't been choosen"));

						ObservableList<Node> nodes = transformConf.getChildren();

						switch (typeTransformation)
						{
								case 1:
										{
										Point2D p = new Point2D();
										String val = ((TextField)nodes.get(1)).getText();		
										p.setX(Double.parseDouble(val),0);	
										val = ((TextField)nodes.get(3)).getText();		
										p.setX(Double.parseDouble(val),1);	

										CController.figures.get(fig.i).shift(p);
										break;
										}
								case 2:
										{
										String val = ((TextField)nodes.get(1)).getText();		
										double deg = Double.parseDouble(val);	

										CController.figures.get(fig.i).rot(deg);
										break;
										}
								case 3:
										{
										int axis = ((RadioButton)nodes.get(2)).isSelected() ? 1 : 0;	
										CController.figures.get(fig.i).symAxis(axis);
										break;
										}
								default:
										throw (new Exception("Type transformation hasn't been choosen"));
						}

						CController.showAlert("Figure was transformed","info",AlertType.INFORMATION);
						log.info("Figure was moved. Figure:\n"+CController.figures.get(fig.i).toString());

				}

				catch (NumberFormatException e)
				{
						CController.showAlert("Some fields are not filled or filled with non-numeric data","err",AlertType.ERROR);
				}

				catch (Exception e)
				{
						CController.showAlert(e.getMessage(),"err",AlertType.ERROR);
				}
		}

		@FXML
		private void cancel()
		{
				CController.showAlert("Finished moving figures","info",AlertType.INFORMATION);
				Stage stage = (Stage)bCancel.getScene().getWindow(); 
				stage.close();
		}
}
