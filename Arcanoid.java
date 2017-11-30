import java.util.* ;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.web.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.animation.AnimationTimer;
import javafx.event.*;
import java.io.* ;
import javafx.scene.canvas.Canvas;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;



public class Arcanoid extends Application {
    

    private BorderPane bp;

    // ici on ajoute la balle le rectangle lequel on va jouer avec
    // et aussi on ajoute les briques


    //create_ball_raquette();



	public void create_brique(int a, int b, int c, int d, String col, Pane p){
	     Rectangle r = new Rectangle();
        r.setX(a);
        r.setY(b);
        r.setWidth(c-a);
        r.setHeight(d-b);
        r.setFill(Color.web(col));
        p.getChildren().add(r);
	}

	public void create_raquette(Pane p){

		Rectangle r = new Rectangle();
		r.setX((p.getPrefHeight()/2)-15);
		r.setY(p.getPrefWidth()-10);
		r.setWidth(30);
		r.setHeight(10);

		//Color c = Color.web("LIGHTCORAL");

		r.setFill(Color.web("black"));
		p.getChildren().addAll(r);
	}

	public void create_ball(Pane p){

		double x = p.getPrefHeight();
		double y = p.getPrefWidth();

//		System.out.println("toto "+x+" "+y);

		Circle ball = new Circle(x/2,(y/2+y/2.5),6);

		p.getChildren().addAll(ball);

	}


    public void makeRectangle(String [] tab, Pane p){
		String [] t1 = tab[0].split(",");
		String [] t2 = tab[1].split(",");

		int h1 = Integer.parseInt(t1[0].substring(1,t1[0].length()));
		int l1 = Integer.parseInt(t1[1].substring(1,t1[1].length()-2));
		
		int h2 = Integer.parseInt(t2[0].substring(2,t2[0].length()));
		int l2 = Integer.parseInt(t2[1].substring(1,t2[1].length()-2));

		String couleur = tab[2].substring(1,tab[2].length());
		//System.out.println("Zouk "+couleur);

		System.out.println(h1+" "+l1+" "+h2+" "+l2);

		create_ball(p);
		create_raquette(p);
		create_brique(h1,l1,h2,l2,couleur,p);
		/*if(h1<0 || h2<0 || l1<0 || l2<0
			|| h1 > p.getPrefWidth() ){
			System.out.println("l'une des briques deborde du Pane ");
		} else {
			create_brique(h1,l1,h2,l2,couleur,p);

		}*/



		//dessiner raquette et balle  

		// dessiner brique

		
	
	}


    public void draw_Ligne(String s, int cpt, Pane nv){
		//String tab [] = s.split(" ");
	  
		if(cpt == 1){ // ici on dessine de cadre

			String tab[] = s.split("x");
		    if (tab.length == 2){
			    int largeur = Integer.parseInt(tab[0].substring(0,tab[0].length()-1));
			    int hauteur = Integer.parseInt(tab[1].substring(1,tab[1].length()));
			  	nv.setPrefSize(hauteur,largeur);
			    nv.setMaxHeight(hauteur);
			    nv.setMaxWidth(largeur);
			    System.out.println(largeur+" "+hauteur);
		    	nv.setStyle("-fx-border-color: black;\n-fx-border-style: solid;\n");
			} else {
				System.out.println("Err de Lecture");
			}	    

		} else { // ici on dessine brique balle etc..
			String tab[] = s.split("&");
		    //nt a = 2;
		    if(tab.length == 3){
		    	makeRectangle(tab,nv);
		    } else {
		    	System.out.println("Err de lecture");
		    }
		}
    }

    public void Draw_level(String chaine){
		int cpt = 1; // ici on decine les HxL sin brique & others
		String tmp = "";
		Pane nv = new  Pane();
		//bp.setCenter(new Label("Pick Ur Level"));
		//nv.setMaxHeight(hauteur);
		//nv.setMaxWidth(largeur);
		//nv.setPrefSize(300,400);
		for( int i = 0; i < chaine.length(); i++){
		    if (chaine.charAt(i) != '\n'){
			tmp += chaine.charAt(i);
		    } else {
			draw_Ligne(tmp,cpt,nv);
			cpt++;
			tmp = ""; // on vide le buffer
		    }
		}
		//bp.getChildren().add(nv);
		//nv.setAlignment(nv,);
		bp.setCenter(nv);
		bp.setAlignment(nv,Pos.TOP_LEFT);
		//bp.setPrefSize(300,400); marche pas aussi..
    }

    public void ReadFile(String fichier){

		String chaine="";
			
		//lecture du fichier texte	
		try{
		    InputStream ips=new FileInputStream(fichier); 
		    InputStreamReader ipsr=new InputStreamReader(ips);
		    BufferedReader br=new BufferedReader(ipsr);
		    String ligne;
		    while ((ligne=br.readLine())!=null){
			//System.out.println(ligne);
			chaine+=ligne+"\n";
		    }
		    br.close(); 
		}		
		catch (Exception e){
		    System.out.println(e.toString());
		}
		// ici chaine contient le contenu de chaque fichier selectionner
		Draw_level(chaine);
    }


    
    public void start(Stage stage){
	
		Menu game = new Menu("Game");
		MenuItem demarrer = new MenuItem("Start       s");
		MenuItem pause =    new MenuItem("Pause     p");
		MenuItem redemarrer = new MenuItem("Restart   r");
		MenuItem exit =     new MenuItem("Exit        e");
		game.getItems().add(demarrer);
		game.getItems().add(pause);
		game.getItems().add(redemarrer);
		game.getItems().add(exit);
		// ce que font les menu items
		//itemAction(stage);
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
			    stage.close();
			}
		    });
		

		
		MenuBar menub = new MenuBar();
		menub.getMenus().add(game);


		VBox vb =  new VBox(6);
		Label lbLevels = new Label("Levels");
		File doc = new File("./Levels");
		vb.getChildren().add(lbLevels);
		int lvl = 1;
		bp = new BorderPane();
		
		for(File f : doc.listFiles()){
		    if(f.isFile()){
			System.out.println(f.getName());
			String s = "Level "+lvl;
			String filelev = "niveau"+lvl;
			Button btn = new Button(s);
			btn.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent e){
				    // lecture du fichier
				    //  bp.setCenter(
				    // Modele m = new Modele();
				    //String filename = "./"+doc.getName()+"/"+filelev;
				    
				    ReadFile("./"+doc.getName()+"/"+filelev);
						 //			    );
				    //System.out.println("toto");
				}
			    });
			vb.getChildren().add(btn);
			lvl++;
		    }
		}
		

		 	    
		
		bp.setTop(menub);
		bp.setLeft(vb);
		bp.setBottom(new Label("Le bas"));
		//bp.setCenter(pouloulou);
		//Pane niveau = new Pane();

		/*Pane canvas = new Pane();
		canvas.setStyle("-fx-background-color: black;");
		canvas.setPrefSize(200,200);*/
		bp.setPrefSize(400,500);
		Scene scene = new Scene(bp) ;
		stage.setScene(scene);
		//stage.setResizable(false);
		stage.setTitle("Arcanoid");
		stage.show();
	
	
    }

    public static void main(String[] args) {
		launch(args);
    }

    
}
