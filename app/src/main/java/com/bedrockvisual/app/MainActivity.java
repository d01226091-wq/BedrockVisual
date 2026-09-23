package com.bedrockvisual.app;

import android.app.*;
import android.os.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.Toast;
import java.util.*;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new MenuView());
    }

    class MenuView extends View {
        Paint p = new Paint(3);
        RectF r = new RectF();
        float sx=1, sy=1;
        String[] labels={"Начать","Настройки","Realms"};
        MenuView(){ super(MainActivity.this); p.setTypeface(Typeface.create("sans",Typeface.NORMAL)); setFocusable(true); }

        void text(Canvas c,String s,float x,float y,float size,int color,Paint.Align a){
            p.setTextSize(size); p.setColor(color); p.setTextAlign(a); p.setTypeface(Typeface.create("sans",Typeface.NORMAL)); c.drawText(s,x,y,p);
        }
        void button(Canvas c,float l,float t,float rr,float bb,String s,boolean purple){
            p.setStyle(Paint.Style.FILL); p.setColor(Color.argb(245,purple?117:205,purple?72:205,purple?229:205)); c.drawRect(l,t,rr,bb,p);
            p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(3); p.setColor(Color.argb(255,80,80,80)); c.drawRect(l,t,rr,bb,p);
            p.setStyle(Paint.Style.FILL); text(c,s,(l+rr)/2,(t+bb)/2+10,28,Color.WHITE,Paint.Align.CENTER);
        }
        @Override protected void onDraw(Canvas c){
            float W=getWidth(), H=getHeight(); sx=W/1536f; sy=H/691f; c.save(); c.scale(sx,sy);
            // dark Nether-like background
            p.setStyle(Paint.Style.FILL);
            p.setShader(new LinearGradient(0,0,0,691,Color.rgb(52,29,25),Color.rgb(8,10,9),Shader.TileMode.CLAMP)); c.drawRect(0,0,1536,691,p); p.setShader(null);
            Random q=new Random(7);
            for(int i=0;i<55;i++){ int x=q.nextInt(1536), y=100+q.nextInt(560), z=20+q.nextInt(90); p.setColor(Color.argb(55,120+q.nextInt(80),60+q.nextInt(70),40)); c.drawRect(x,y,x+z,y+z/2,p); }
            // title
            p.setTypeface(Typeface.create("sans",Typeface.BOLD)); text(c,"ПОДПИШИСЬ НА КАНАЛ С РПО ОНИ",768,132,39,Color.WHITE,Paint.Align.CENTER);
            text(c,"ВЫХОДЯТ КАЖДЫЙ ДЕНЬ",768,184,39,Color.WHITE,Paint.Align.CENTER);
            text(c,"ТГК: @BEDROCKGO",768,236,39,Color.WHITE,Paint.Align.CENTER);
            p.setTypeface(Typeface.DEFAULT);
            // central buttons
            button(c,560,276,975,356,"Начать",false);
            button(c,560,368,975,447,"Настройки",false);
            button(c,560,459,975,538,"Realms",true);
            // social / player panel
            button(c,1318,17,1518,80,"⚭  Соцсеть (0)",false);
            button(c,1180,543,1332,611,"Гардеробная",false);
            button(c,105,547,265,609,"▣  Профиль",false);
            // player silhouette
            p.setColor(Color.rgb(150,55,55)); c.drawRect(1218,338,1292,475,p); c.drawRect(1203,306,1300,365,p);
            p.setColor(Color.rgb(240,220,220)); c.drawRect(1220,362,1240,398,p); c.drawRect(1270,362,1290,398,p);
            // bottom addon strip
            p.setColor(Color.argb(220,90,190,210)); c.drawRect(610,545,975,626,p);
            text(c,"Рынок",790,590,24,Color.BLACK,Paint.Align.CENTER);
            text(c,"Add-ons!",900,570,27,Color.YELLOW,Paint.Align.CENTER);
            text(c,"© Mojang AB",0,684,25,Color.WHITE,Paint.Align.LEFT);
            text(c,"v26.40",1515,684,25,Color.WHITE,Paint.Align.RIGHT);
            c.restore();
        }
        @Override public boolean onTouchEvent(android.view.MotionEvent e){
            if(e.getAction()!=MotionEvent.ACTION_UP) return true;
            float x=e.getX()/sx, y=e.getY()/sy;
            if(x>=560&&x<=975&&y>=276&&y<=356) toast("Начать");
            else if(x>=560&&x<=975&&y>=368&&y<=447) toast("Настройки");
            else if(x>=560&&x<=975&&y>=459&&y<=538) toast("Realms");
            else if(x>=1180&&x<=1332&&y>=543&&y<=611) toast("Гардеробная");
            else if(x>=105&&x<=265&&y>=547&&y<=609) toast("Профиль");
            return true;
        }
        void toast(String s){ Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show(); }
    }
}
