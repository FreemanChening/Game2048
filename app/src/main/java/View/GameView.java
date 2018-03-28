package View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import Service.GameService;

/**
 * Created by admin on 2018/1/22.
 */

public class GameView extends View {

    GameService gameService;

    public GameView(Context context, AttributeSet attrs){
        super(context,attrs);
        gameService = new GameService();
        setOnTouchListener(new OnTouchListener() {
            float startX=0;
            float startY=0;
            float endX=0;
            float endY=0;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX=event.getX();
                        startY=event.getY();
                        break;

                    case MotionEvent.ACTION_UP:
                        endX=event.getX();
                        endY=event.getY();

                        float offsetX=endX-startX;
                        float offsetY=endY-startY;
                        if (Math.abs(offsetX)>Math.abs(offsetY)){
                            if (offsetX<0){
                                //left
                                //Toast.makeText(GameView.this.getContext(),"向左滑动",1000).show();
                                gameService.swipLeft();
                            }else if (offsetX>0){
                                //right
                                //Toast.makeText(GameView.this.getContext(), "向右滑动",1000).show();
                                gameService.swipRight();
                            }
                        }else {
                            if (offsetY<0){
                                //up
                                //Toast.makeText(GameView.this.getContext(),"向上滑动",1000).show();
                                gameService.swipUp();
                            }else if (offsetY>0){
                                //down
                                //Toast.makeText(GameView.this.getContext(),"向下滑动",1000).show();
                                gameService.swipDown();
                            }
                        }
                        invalidate();

                        if (gameService.isWin()){
                            new AlertDialog.Builder(
                                    GameView.this.getContext()).setMessage("你赢了，真厉害").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    initView();
                                }
                            }).show();
                        }
                        if (gameService.isFail()){
                            new AlertDialog.Builder(
                                    GameView.this.getContext()).setMessage("很遗憾，你输了").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    initView();
                                }
                            }).show();
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void initView(){
        initScore();
        gameService.startNewGame();
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //gameService=new GameService();



        int rectWidth = (getWidth() - 60*2 - 10*5)/4;

        Paint paint = new Paint();
        //int rectWidth = getWidth()/4;

        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(60,60,getWidth()-60,getWidth()-60,paint);

        paint.setTextSize(40);
        paint.setColor(Color.YELLOW);
        canvas.drawText(String.valueOf(gameService.getScore()),80,30,paint);
        canvas.drawText(String.valueOf(getBest()),80+rectWidth,30,paint);
        paint.setStyle(Paint.Style.FILL);

//遍历二维数组
        int x =0;
        int y =0;
        for (int i = 0;i < 4;i++){
            for (int j = 0;j < 4;j++){
                //计算元素对应方格在屏幕中的坐标
                x = 70 + (rectWidth + 10)*j;
                y = 70 + (rectWidth + 10)*i;
                String number = String.valueOf(gameService.getNumber(i,j));
                if(number.equals("0")){
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(x, y, x + rectWidth, y + rectWidth,paint);
                } else {
                    paint.setColor(Color.GRAY);
                    canvas.drawRect(x,y,x + rectWidth,y + rectWidth,paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(rectWidth - number.length() * rectWidth
                            / 6);
                    canvas.drawText(number,
                            x + (rectWidth+10)/5 - number.length() *5,
                            y + (rectWidth+10)*2/3,paint);
                }
            }
        }
    }

    private void saveBest(int s){
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        e.putInt("best",s);
        e.commit();
    }

    private int getBest(){
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("best",0);
    }

    private void initScore(){
        if(gameService.getScore()>getBest()){
            saveBest(gameService.getScore());
        }
        gameService.setScore(0);
    }


}
