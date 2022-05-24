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
public class FindIntersection
{

		class ObservableOption
		{
				public ObservableOption(int i,int l, String s)
				{
						this.i = i;
						this.s = s;
						this.l = l;
				}
				public int i;
				public int l;
				public String s;
				public String toString()
				{
						return s;
				}
		}

		private int totalFigures;
		private ObservableList<ObservableOption> figuresOfType;
		private ObservableList<ObservableOption> leftFigures;
		private ObservableList<ObservableOption> rightFigures;
		private int leftIndex;
		private int rightIndex;
		private boolean ignoreLeft;
		private boolean ignoreRight;

		@FXML
		private Button bCancel;

		@FXML
		private ComboBox cbTypesFigures;

		@FXML
		private ComboBox cbFiguresLeft;

		@FXML
		private ComboBox cbFiguresRight;

		@FXML
		private void initialize() throws Exception
		{
				ignoreLeft = false;
				ignoreRight = false;
				int countTypes = 8;
				String[] sTypes = {"Segment","Circle","Polyline","NGon","TGon","QGon","Rectangle","Trapeze"};
				ObservableList<ObservableOption> typeNames = FXCollections.observableArrayList();
				for(int i = 1; i < countTypes+1; ++i)
				{
						typeNames.add(new ObservableOption(i,i,sTypes[i-1]));
				}

				cbTypesFigures.setItems(typeNames);
		}
		
		@FXML
		private void changeTypeFigure()
		{
				totalFigures = CController.figures.size();

				
				int type = ((ObservableOption)cbTypesFigures.getValue()).i;	

				figuresOfType = FXCollections.observableArrayList();

				leftFigures = FXCollections.observableArrayList();
				rightFigures = FXCollections.observableArrayList();

				leftIndex = -1;
				rightIndex = -1;
				switch(type)
				{

						case 1:
						{
								for(int i = 0,l=0; i < totalFigures; ++i)
								{
										if(CController.figures.get(i).getClass() == Segment.class)
										{
												figuresOfType.add(new ObservableOption(i,l,CController.figures.get(i).toString()));
												++l;
										}
								}
								break;
						}

						case 2:
						{
								for(int i = 0,l=0; i < totalFigures; ++i)
								{
										if(CController.figures.get(i).getClass() == Circle.class)
										{
												figuresOfType.add(new ObservableOption(i,l,CController.figures.get(i).toString()));
												++l;
										}
								}
								break;
						}

						case 3:
						{
								for(int i = 0,l=0; i < totalFigures; ++i)
								{
										if(CController.figures.get(i).getClass() == Polyline.class)
										{	
												figuresOfType.add(new ObservableOption(i,l,CController.figures.get(i).toString()));
												++l;
										}
								}
								break;
						}

						case 4:
						{
								for(int i = 0,l=0; i < totalFigures; ++i)
								{
										if(CController.figures.get(i).getClass() == NGon.class)
										{	
												figuresOfType.add(new ObservableOption(i,l,CController.figures.get(i).toString()));
												++l;
										}
								}
								break;
						}

						case 5:
						{
								for(int i = 0,l=0; i < totalFigures; ++i)
								{
										if(CController.figures.get(i).getClass() == TGon.class)
										{	
												figuresOfType.add(new ObservableOption(i,l,CController.figures.get(i).toString()));
												++l;
										}
								}
								break;
						}

						case 6:
						{
								for(int i = 0,l=0; i < totalFigures; ++i)
								{
										if(CController.figures.get(i).getClass() == QCon.class)
										{	
												figuresOfType.add(new ObservableOption(i,l,CController.figures.get(i).toString()));
												++l;
										}
								}
								break;
						}

						case 7:
						{
								for(int i = 0,l=0; i < totalFigures; ++i)
								{
										if(CController.figures.get(i).getClass() == Rectangle.class)
										{	
												figuresOfType.add(new ObservableOption(i,l,CController.figures.get(i).toString()));
												++l;
										}
								}
								break;
						}

						case 8:
						{
								for(int i = 0,l=0; i < totalFigures; ++i)
								{
										if(CController.figures.get(i).getClass() == Trapeze.class)
										{	
												figuresOfType.add(new ObservableOption(i,l,CController.figures.get(i).toString()));
												++l;
										}
								}
								break;
						}

						default:
								break;
				}

				for (int i = 0; i< figuresOfType.size(); ++i)
				{
						leftFigures.add(figuresOfType.get(i));
						rightFigures.add(figuresOfType.get(i));
				}
				cbFiguresLeft.setItems(leftFigures);
				cbFiguresRight.setItems(rightFigures);

		}

		@FXML
		private void changeLeft()
		{

		}

		@FXML
		private void changeRight()
		{
				
		}

		@FXML
		private void confirm()
		{
				try
				{
						ObservableOption type = (ObservableOption)cbTypesFigures.getValue();	
						if (type == null)
								throw (new Exception("Type of figures hasn't been choosen"));

						ObservableOption left = (ObservableOption)cbFiguresLeft.getValue();	
						if (left == null)
								throw (new Exception("left figure hasn't been choosen"));
						
						ObservableOption right = (ObservableOption)cbFiguresRight.getValue();	
						if (right == null)
								throw (new Exception("right figure hasn't been choosen"));
						
						if(left.i == right.i)
								throw (new Exception("same figure has been choosen twice"));

						CController.textValue1 = "Is there intersection? :";
						CController.textValue2 = CController.figures.get(left.i).cross(CController.figures.get(right.i))? "True" : "False";
						CController.markedFigureIndexes = new int[] {left.i,right.i};

						CController.showAlert("Intersection was calculated","info",AlertType.INFORMATION);
						log.info("Intersection found between:\n"+CController.figures.get(left.i).toString()
								+"\nand\n"+CController.figures.get(right.i).toString());

						Stage stage = (Stage)bCancel.getScene().getWindow(); 
						stage.close();
				}

				catch (Exception e)
				{
						CController.showAlert(e.getMessage(),"err",AlertType.ERROR);
				}
		}

		@FXML
		private void cancel()
		{
				CController.showAlert("Finished mesuarements","info",AlertType.INFORMATION);
				Stage stage = (Stage)bCancel.getScene().getWindow(); 
				stage.close();
		}
}
//ObservableOption choosen = (ObservableOption)cbFiguresRight.getValue();
//				if(choosen == null)
//						return;
//				if(ignoreRight)
//				{
//						ignoreRight = false;
//						return;
//				}
//				int right = choosen.l;
//
//				cbFiguresLeft.getItems().clear();
//				
//				for (int i = 0; i< figuresOfType.size(); ++i)
//				{
//						leftFigures.add(figuresOfType.get(i));
//				}
//				cbFiguresLeft.setItems(leftFigures);
//				cbFiguresLeft.getItems().remove(right);
//				if (leftIndex >= 0)
//				{
//						cbFiguresLeft.getSelectionModel().select(leftIndex);
//						ignoreLeft = true;
//				}
//				rightIndex = right;
