package BatailleNavale;

import java.util.Random;

/**
 * Created by fabie_000 on 16/05/2017.
 */
public class Model {
    public static final int JOUEUR_1=1;
    public static final int JOUEUR_2=2;
    private Bateau[] joueur1, joueur2;
    private Coordonnee[] tir1, tir2;
    private int indiceDernierCoupJoueur1,indiceDernierCoupJoueur2;
    private int joueurEnCours;
    private boolean placementBateau;

    public Model() {
        joueur1 = new Bateau[]{
                new Bateau(5), new Bateau(4), new Bateau(3), new Bateau(3), new Bateau(2)
        };
        tir1 = new Coordonnee[100];
        tir2 = new Coordonnee[100];
        joueur2 = new Bateau[]{
                new Bateau(5), new Bateau(4), new Bateau(3), new Bateau(3), new Bateau(2)
        };

        for (int i = 0; i < joueur1.length; i++) {
            placeBateau(joueur1[i]);
            placeBateau(joueur2[i]);
        }
        indiceDernierCoupJoueur1=0;
        indiceDernierCoupJoueur2=0;
        joueurEnCours =1;
        placementBateau=true;
    }


    public boolean isPlacementBateau() {
        return placementBateau;
    }

    public void setPlacementBateau(boolean placementBateau) {
        this.placementBateau = placementBateau;
    }

    public Bateau[] getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Bateau[] joueur1) {
        this.joueur1 = joueur1;
    }

    public Bateau[] getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Bateau[] joueur2) {
        this.joueur2 = joueur2;
    }

    public Coordonnee[] getTir1() {
        return tir1;
    }

    public void setTir1(Coordonnee[] tir1) {
        this.tir1 = tir1;
    }

    public Coordonnee[] getTir2() {
        return tir2;
    }

    public void setTir2(Coordonnee[] tir2) {
        this.tir2 = tir2;
    }

    public int getIndiceDernierCoupJoueur1() {
        return indiceDernierCoupJoueur1;
    }

    public void setIndiceDernierCoupJoueur1(int indiceDernierCoupJoueur1) {
        this.indiceDernierCoupJoueur1 = indiceDernierCoupJoueur1;
    }

    public int getIndiceDernierCoupJoueur2() {
        return indiceDernierCoupJoueur2;
    }

    public void setIndiceDernierCoupJoueur2(int indiceDernierCoupJoueur2) {
        this.indiceDernierCoupJoueur2 = indiceDernierCoupJoueur2;
    }

    public int getJoueurEnCours() {
        return joueurEnCours;
    }

    public void setJoueurEnCours(int joueurEnCours) {
        this.joueurEnCours = joueurEnCours;
    }

    public boolean isCoupValide(Coordonnee xy){
        switch (joueurEnCours){
            case 1:
                for (int i = 0; i < indiceDernierCoupJoueur1; i++) {
                    if (tir1[i].equals(xy)){
                        return false;
                    }
                }
                return true;
            case 2:
                for (int i = 0; i < indiceDernierCoupJoueur2; i++) {
                    if (tir2[i].equals(xy)){
                        return false;
                    }
                }
                return true;
            default:
                return false;
        }
    }

    public boolean isBateauAt(Coordonnee xy){
        for (int i = 0; i < joueur2.length; i++) {
            Coordonnee[] tab;
            if (joueurEnCours==JOUEUR_1){
                if (isPlacementBateau()){
                    tab = joueur1[i].getPosition();
                }
                else{
                    tab = joueur2[i].getPosition();
                }
            }
            else{
                if (isPlacementBateau()){
                    tab = joueur2[i].getPosition();
                }
                else{
                    tab = joueur1[i].getPosition();
                }
            }
            for (int j = 0; j < tab.length; j++) {
                if (tab[j]!=null){
                    if (tab[j].equals(xy)){
                        updateBateau(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void placeBateau(Bateau bateau) {
        int choixSens1;
        int choixSens2;
        int l;
        int c;
        Random rd = new Random();
        Coordonnee[] tab = bateau.getPosition();


        choixSens1 = rd.nextInt(2); // 0 pour horizontal, 1 pour vertical
        choixSens2=rd.nextInt(2); // 0 pour gauche ou haut, 1 pour droite ou bas
        l=rd.nextInt(10);
        c=rd.nextInt(10);
        while (isBateauAt(new Coordonnee(l, c))){
            l=rd.nextInt(10);
            c=rd.nextInt(10);
        }
        tab[0] = new Coordonnee(l,c);
        int coordEnCours=1;
        int casePos =1;
        if (choixSens1==0){
            if (choixSens2==0){
                int n = -1;
                while (casePos!= bateau.getTaille()){
                    if (c+n<0 || isBateauAt(new Coordonnee(l, c+n))){
                        if (n<0){
                            n=1;
                        }
                        else{
                            while (isBateauAt(new Coordonnee(l, c))){
                                l=rd.nextInt(10);
                                c=rd.nextInt(10);
                            }
                            tab = new Coordonnee[bateau.getTaille()];
                            tab[0]= new Coordonnee(l,c);
                            coordEnCours=1;
                            casePos=1;
                        }
                    }
                    else{
                        casePos++;
                        tab[coordEnCours] = new Coordonnee(l, c+n);
                        coordEnCours++;
                        if (n<0){
                            n--;
                        }
                        else{
                            n++;
                        }
                    }
                }
            }
            else{
                int n = 1;
                while (casePos!= bateau.getTaille()){
                    if (c+n>9 || isBateauAt(new Coordonnee(l, c+n))){
                        if (n>0){
                            n=-1;
                        }
                        else{
                            while (isBateauAt(new Coordonnee(l, c))){
                                l=rd.nextInt(10);
                                c=rd.nextInt(10);
                            }
                            tab = new Coordonnee[bateau.getTaille()];
                            tab[0]= new Coordonnee(l,c);
                            casePos=1;
                        }
                    }
                    else{
                        casePos++;
                        tab[coordEnCours] = new Coordonnee(l, c+n);
                        coordEnCours++;
                        if (n<0){
                            n--;
                        }
                        else{
                            n++;
                        }

                    }
                }
            }
        }
        else {
            if (choixSens2==0){
                int n = -1;
                while (casePos!= bateau.getTaille()){
                    if (l+n<0 || isBateauAt(new Coordonnee(l+n, c))){
                        if (n<0){
                            n=1;
                        }
                        else{
                            while (isBateauAt(new Coordonnee(l, c))){
                                l=rd.nextInt(10);
                                c=rd.nextInt(10);
                            }
                            tab = new Coordonnee[bateau.getTaille()];
                            tab[0]= new Coordonnee(l,c);
                            coordEnCours=1;
                            casePos=1;
                        }
                    }
                    else{
                        casePos++;
                        tab[coordEnCours] = new Coordonnee(l+n, c);
                        coordEnCours++;
                        if (n<0){
                            n--;
                        }
                        else{
                            n++;
                        }
                    }
                }
            }
            else{
                int n = 1;
                while (casePos!= bateau.getTaille()){
                    if (l+n>9 || isBateauAt(new Coordonnee(l+n, c))){
                        if (n>0){
                            n=-1;
                        }
                        else{
                            while (isBateauAt(new Coordonnee(l, c))){
                                l=rd.nextInt(10);
                                c=rd.nextInt(10);
                            }
                            tab = new Coordonnee[bateau.getTaille()];
                            tab[0]= new Coordonnee(l,c);
                            casePos=1;
                        }
                    }
                    else{
                        casePos++;
                        tab[coordEnCours] = new Coordonnee(l+n, c);
                        coordEnCours++;
                        if (n<0){
                            n--;
                        }
                        else{
                            n++;
                        }

                    }
                }
            }
        }
    }

    public void updateBateau(int i){
        Bateau b;
        if (joueurEnCours==JOUEUR_1){
            b = joueur2[i];
        }
        else {
            b = joueur1[i];
        }
        switch (b.getState()){
            case SAIN:
                b.incrementeCaseTouché();
                b.setState(Bateau.Etat.TOUCHE);
                break;
            case TOUCHE:
                b.incrementeCaseTouché();
                if (b.getCaseTouché()==b.getTaille()){
                    b.setState(Bateau.Etat.COULE);
                }
                break;
        }
        if (joueurEnCours==JOUEUR_1){
            joueur2[i]=b;
        }
        else {
            joueur1[i]=b;
        }
    }

    public boolean partieTerminee(){
        for (int i = 0; i < this.joueur1.length; i++) {
            Bateau b = null;
            if (this.joueurEnCours==JOUEUR_1){
                b = this.joueur2[i];
            }
            else{
                b = this.joueur1[i];
            }
            if (b.getTaille()!=b.getCaseTouché()){
                return false;
            }
        }
        return true;
    }
}

class Bateau{
    public enum Etat {SAIN, TOUCHE, COULE}
    private int taille;
    private int caseTouché;
    private Coordonnee[] position;
    private Etat state;

    public Bateau(int taille) {
        this.taille = taille;
        this.caseTouché=0;
        this.position = new Coordonnee[this.taille];
        this.state = Etat.SAIN;
    }

    public int getTaille() {
        return taille;
    }

    public int getCaseTouché() {
        return caseTouché;
    }

    public void incrementeCaseTouché() {
        this.caseTouché++;
    }

    public void setCaseTouché(int nb) {
        this.caseTouché=nb;
    }

    public Etat getState() {
        return state;
    }

    public void setState(Etat state) {
        this.state = state;
    }

    public Coordonnee[] getPosition() {
        return position;
    }

    public void setPosition(Coordonnee[] position) {
        this.position = position;
    }

    public void displayCoord(){
        for (int i = 0; i < position.length; i++) {
            System.out.println(position[i]);
        }
    }
}