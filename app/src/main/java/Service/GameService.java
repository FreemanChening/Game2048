package Service;

import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by admin on 2018/1/22.
 */


public class GameService {

    private int[][] numbers;

    private  int score = 0;

    private List<Point> emptySpots;
    public GameService() {

        numbers = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};

        emptySpots = new ArrayList<Point>();

        for (int i = 0;i < 5; i++){
            spawn(2);
        }
    }

    public int getNumber(int r,int c){
        return numbers[r][c];
    }

    public void spawn (int n){
        emptySpots.clear();
        for (int i = 0; i < 4; i++){
            for(int j  = 0; j <4 ;j++){
                if(numbers[i][j]==0){
                    Point point = new Point(i, j);
                    emptySpots.add(point);
                }
            }
        }
        if (emptySpots.size()!=0){
            Random random = new Random();
            int ran = random.nextInt(emptySpots.size());
            Point point = emptySpots.get(ran);
            numbers[point.x][point.y] = n;
        }
    }


    public void startNewGame(){
        for (int i = 0; i < 4; i++){
            for(int j  = 0; j <4 ;j++){
                numbers[i][j] = 0;
            }
        }
        for (int i = 0;i < 5; i++){
            spawn(2);
        }
        score = 0;
    }



    public void swipLeft() {
        boolean isMove = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = j+1; k < 4; k ++){

                    if(numbers[i][k]>0){
                        if(numbers[i][j]==0){
                            numbers[i][j]=numbers[i][k];
                            numbers[i][k]=0;
                            j--;
                            isMove=true;
                        }
                        else if(numbers[i][j]==numbers[i][k]){
                            numbers[i][j]*=2;
                            numbers[i][k]=0;
                            score+=numbers[i][j];
                            isMove=true;
                        }
                        break;
                    }
                }
            }
        }
        if(isMove){
            spawn(2);
        }
    }

    public void swipRight(){
        boolean isMove = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 4-1; j >= 1; j--) {
                for (int k = j-1; k >= 0; k --){

                    if(numbers[i][k]>0){
                        if(numbers[i][j]<=0){
                            numbers[i][j]=numbers[i][k];
                            numbers[i][k]=0;
                            j++;
                            isMove=true;
                        }
                        else if(numbers[i][j]==numbers[i][k]){
                            numbers[i][j]*=2;
                            numbers[i][k]=0;
                            score+=numbers[i][j];
                            isMove=true;
                        }
                        break;
                    }
                }
            }
        }
        if(isMove){
            spawn(2);
        }
    }

    public void swipUp(){
        boolean isMove = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = j+1; k < 4; k ++){

                    if(numbers[k][i]>0){
                        if(numbers[j][i]<=0){
                            numbers[j][i]=numbers[k][i];
                            numbers[k][i]=0;
                            j--;
                            isMove=true;
                        }
                        else if(numbers[j][i] == numbers[k][i]){
                            numbers[j][i]*=2;
                            numbers[k][i]=0;
                            score+=numbers[j][i];
                            isMove=true;
                        }
                        break;
                    }
                }
            }
        }
        if(isMove){
            spawn(2);
        }
    }


    public void swipDown(){
        boolean isMove = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 4-1; j >= 1; j--) {
                for (int k = j-1; k >= 0; k --){

                    if(numbers[k][i]>0){
                        if(numbers[j][i]<=0){
                            numbers[j][i] = numbers[k][i];
                            numbers[k][i] = 0;
                            j++;
                            isMove=true;
                        }
                        else if(numbers[j][i]==numbers[k][i]){
                            numbers[j][i]*=2;
                            numbers[k][i]=0;
                            score+=numbers[j][i];
                            isMove=true;
                        }
                        break;
                    }
                }
            }
        }
        if(isMove){
            spawn(2);
        }

    }

    public int getScore(){
        return score;
    }

    public void setScore(int n){
        score = n;
    }

    public boolean isWin(){
        for (int i = 0;i < 4; i++){
            for (int j = 0;j <4; j++){
                if(numbers[i][j]==2048){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFail(){
        for (int i = 0;i < 4; i++){
            for (int j = 0;j <4; j++){
                if(numbers[i][j] == 0
                        ||(i>0 && numbers[i][j] == numbers[i - 1][j])
                        ||(i<3 && numbers[i][j] == numbers[i + 1][j])
                        ||(j>0 && numbers[i][j] == numbers[i][j - 1])
                        ||(j<3 && numbers[i][j] == numbers[i][j + 1])
                        ){
                    return false;
                }
            }
        }
        return true;
    }


}
