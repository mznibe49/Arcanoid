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
import javafx.scene.input.*;



public class Arcanoid extends Application {
    

    private BorderPane bp;
    private Rectangle r;
    private boolean play = false;
    private Pane center;
    private String leeeeeevl = "";
    AnimationTimer anim;
 

    public int abs(int x){
    	if(x<0) return -x;
    	return x;
    }


	public void create_brique(int a, int b, int c, int d, String col, Pane p){

	    Rectangle r = new Rectangle(a,b,abs(c-a),abs(d-b));
        r.setFill(Color.web(col));
        p.getChildren().add(r);
	}


	public LinkedList<Rectangle> make_list(){
	    return null;
	}

	public void create_raquette(Pane p){

		this.r = new Rectangle();
		r.setX((p.getPrefWidth()/2)-15);
		r.setY(p.getPrefHeight()-10);
		r.setWidth(30);
		r.setHeight(10);
		//r.setStyle("-fx-stroke: red;");


		r.setFill(Color.web("black"));
		p.getChildren().add(r);
	}

	public boolean existRaquette(double nPosX){
		return  nPosX >=  r.getX()  && nPosX <= r.getX()+r.getWidth();
	}

	public void create_ball(Pane p){

		double x = p.getPrefWidth();
		double y = p.getPrefHeight();

//		System.out.println("toto "+x+" "+y);

		Circle ball = new Circle(x/2,y-16,6);

		p.getChildren().add(ball);

		anim = new AnimationTimer() {

			//double xSpeed = 200E-9, ySpeed = 120E-9; // px per
			//long lastTime = System.nanoTime();
		    double dx = 2.5, dy = 2.5; 

		    public void handle(long time) {
				//long dt = time - lastTime;
				//double dx = xSpeed*dt , dy = ySpeed*dt ;

				double posX = ball.getCenterX(), posY = ball.getCenterY();
				double nPosX = posX + dx, nPosY = posY + dy;


				if(play){

					//System.out.println( posX+" "+posY+" "+nPosX+" "+nPosY );

					if (nPosX < ball.getRadius() || (nPosX > p.getPrefWidth() - ball.getRadius() ) ) {
					    dx =-dx;
				//	    xSpeed = -xSpeed;
					} else
					
					if (nPosY < ball.getRadius() || nPosY > p.getPrefHeight() - ball.getRadius() ) {
					    dy = -dy;
				//	    ySpeed = -ySpeed;
					}

					// nPosY 400							// 390
					if(nPosY >= r.getY() - ball.getRadius()){ //
						dx=2.5 ; dy = 2.5;
				//		dx = (200E-9)*dt ; dy = (120E-9)*dt ;

                            if(nPosX  == (r.getX() + r.getWidth()/2)){ 
                                dy=-dy;
                                dx=0;
                        //         ySpeed = -ySpeed;
                         //        xSpeed = 0;                
                            } 
                  
                            else if((nPosX > (r.getX() + r.getWidth()/2)) && (nPosX<=(r.getX() + r.getWidth()))){
                                 dy=-dy;
                                 dx=0.5*dx;
                         //        ySpeed = -ySpeed;
                           //      xSpeed = 0.5*xSpeed;


                            }
                            
                            else if ((nPosX < (r.getX() + r.getWidth()/2)) && (nPosX>=r.getX())){
                                dy=-dy;
                                dx=-0.5*dx;
                             //   ySpeed = -ySpeed;
                              //  xSpeed = -0.5*xSpeed;

                			}


                			// le cas du end of game
                			else {
                				play = false;
                				bp.setBottom(new Label("You Lost ! :s:s"));
                				this.stop();
                				//dx=2.5 ; dy = 2.5;
                			}
							
        			} 
        			

					ball.setCenterX(posX + dx);
					ball.setCenterY(posY + dy);
					//lastTime = time;
				} /*else {
					ball.setCenterX(posX );
					ball.setCenterY(posY );
					//lastTime = time;
				}*/
		    }

		};
		anim.start();

	}


    public int makeRectangle(String [] tab, Pane p){

		String [] t1 = tab[0].split(",");
		String [] t2 = tab[1].split(",");

		int h1 = Integer.parseInt(t1[0].substring(1,t1[0].length()));
		int l1 = Integer.parseInt(t1[1].substring(1,t1[1].length()-2));
		
		int h2 = Integer.parseInt(t2[0].substring(2,t2[0].length()));
		int l2 = Integer.parseInt(t2[1].substring(1,t2[1].length()-2));

		String couleur = tab[2].substring(1,tab[2].length());
		//System.out.println("Zouk "+couleur);
		System.out.println(h1+" "+l1+" "+h2+" "+l2+" "+couleur);
		if(l1 >= p.getPrefWidth() || h1 >= p.getPrefWidth() || l2 >= p.getPrefHeight()|| h2 >= p.getPrefHeight() ) return 0;		
		create_brique(h1,l1,h2,l2,couleur,p);			
		return 1;	
	}

	public Pane init_pane(){
	 	Pane p = new Pane();
		p.setPrefSize(300,400);
		p.setMaxWidth(300);
		p.setMaxHeight(400);
		create_raquette(p);	
		p.setStyle("-fx-border-color: black;\n-fx-border-style: solid;\n");
		return p;  	
	}




    public int  draw_Ligne(String s, int cpt, Pane nv){


		if(cpt == 1){ // ici on dessine de cadre

			String tab[] = s.split("x");
		    if (tab.length == 2){
			    int largeur = Integer.parseInt(tab[0].substring(0,tab[0].length()-1));
			    int hauteur = Integer.parseInt(tab[1].substring(1,tab[1].length()));
			  	nv.setPrefSize(largeur,hauteur);
			    nv.setMaxWidth(largeur);
			    nv.setMaxHeight(hauteur);
			    System.out.println(largeur+" "+hauteur);

			    if(hauteur <= 0 || largeur <= 0)
			    	return 0;
			} else  // si on a 300 k 400 ça marche pas on veut le "x" au milieu
				return 0;
			 
		} else { // ici on dessine brique balle etc..

			String tab[] = s.split("&");

		    if(tab.length == 3)
		    	return makeRectangle(tab,nv);
		    else 
		    	return 0;    
		}

		return 1;
    }

    public Pane Draw_level(String chaine){
    	//this.start = 0;
    	play = false;
		int cpt = 1; // ici on dessine les HxL sin brique & others
		String tmp = "";
		Pane nv = new  Pane();
		Pane def = init_pane();
		int res = 0;

		for( int i = 0; i < chaine.length(); i++){
		    if (chaine.charAt(i) != '\n'){
				tmp += chaine.charAt(i);
		    } else {
				res = draw_Ligne(tmp,cpt,nv);
				if (res == 0) break;
				cpt++;
				tmp = ""; // on vide le buffer
		    }
		}


		if (res == 1){
			bp.setCenter(nv);
			bp.setBottom(new Label("Press S to start."));
			bp.setAlignment(nv,Pos.TOP_LEFT);
			create_ball(nv);
			create_raquette(nv);	
			nv.setStyle("-fx-border-color: black;\n-fx-border-style: solid;\n");
			return nv;
		}else{
			bp.setCenter(def);
			bp.setBottom(new Label("Unable To Load"));
			bp.setAlignment(def,Pos.TOP_LEFT);
			return def;
		} 
		//return null;
		
    }



    public Pane ReadFile(String fichier){

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
		return Draw_level(chaine);
    }


    
    public void start(Stage stage){
	
		Menu game = new Menu("Game");
		MenuItem demarrer = new MenuItem("Start       s");
		MenuItem pause =    new MenuItem("Pause     p");
		MenuItem redemarrer = new MenuItem("Restart   r");
		MenuItem exit =     new MenuItem("Exit        esc");
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

		demarrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				play = true;
			//		start = 1;
			}
		});	

		pause.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				play = false;
			//		start = 1;
			}
		});		
		
		redemarrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				center = ReadFile(leeeeeevl);
    			bp.setCenter(center);
    			play=false;
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
		//Pane pane_actu = new Pane();
		
		for(File f : doc.listFiles()){
		    if(f.isFile()){
			//System.out.println(f.getName());
			String s = "Level "+lvl;
			String filelev = "niveau"+lvl;
			Button btn = new Button(s);
			btn.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent e){
				    leeeeeevl = "./"+doc.getName()+"/"+filelev ;
				    center = ReadFile(leeeeeevl);
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
		Scene scene = new Scene(bp,400,500) ;
		stage.setScene(scene);
		stage.setTitle("Arcanoid");
		stage.show();

		 scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                  @Override
                  public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                      move = 0;
                    
                    }
                  }
                }); 
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			public void handle(KeyEvent e) {
    			if(e.getCode() == KeyCode.ESCAPE)
    			 	stage.close();
    			 if(e.getCode() == KeyCode.S){
    				play = true;
                                 
    				System.out.println("S");
    				bp.setBottom(new Label("In Game"));
    			}
                  
    			if(e.getCode() == KeyCode.P){
    				play = false;
    				//anim.stop();
    			    System.out.println("P");
    			    bp.setBottom(new Label("Game in Pause"));
    			}
    			if(e.getCode() == KeyCode.R){ // redemarrer
    				
                    anim.stop();
    				center = ReadFile(leeeeeevl);
    				play=false;
    			}	
                         if (e.getCode()==KeyCode.RIGHT) { // don't use toString here!!!
                      move = 2;
                    }   
                        if (e.getCode() == KeyCode.LEFT) {
                      move = 1;
                    }

		/* scene.setOnKeyPressed(new EventHandler<KeyEvent>(){


			public void handle(KeyEvent e) {
				//pressKey(e.getCharacter().charAt(0));
    			if(e.getCode() == KeyCode.ESCAPE)
    			 	stage.close();
    			 if(e.getCode() == KeyCode.S){
    				play = true;
    				System.out.println("S");
    				bp.setBottom(new Label("In Game"));
    			}
    			if(e.getCode() == KeyCode.P){
    				play = false;
    				//anim.stop();
    			    System.out.println("P");
    			    bp.setBottom(new Label("Game in Pause"));
    			}
    			if(e.getCode() == KeyCode.R){ // redemarrer

    				ReadFile(leeeeeevl);
    				play=false;
    			}	
    			if(play){
    				if((e.getCode() == KeyCode.RIGHT) && (r.getX()+r.getWidth()+5 < center.getPrefWidth()))
	    				r.setX(r.getX()+5);
	    			if((e.getCode() == KeyCode.LEFT) && (r.getX()-5 > 0))
	    				r.setX(r.getX()-5);
    			}
			}
		    }); */
	
	
    
	}

    public static void main(String[] args) {
		launch(args);
    }

    
}
