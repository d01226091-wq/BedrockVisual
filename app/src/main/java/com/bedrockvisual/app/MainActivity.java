package com.bedrockvisual.app;

import android.app.*;
import android.os.*;
import android.graphics.*;
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
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        float sx=1, sy=1;
        int screen=0; // 0 home, 1 start, 2 settings, 3 realms, 4 wardrobe, 5 profile
        boolean music=true, neon=true;

        MenuView() {
            super(MainActivity.this);
            setFocusable(true);
            p.setTypeface(Typeface.create("sans", Typeface.NORMAL));
        }

        void text(Canvas c,String s,float x,float y,float size,int color,Paint.Align a,boolean bold){
            p.setShader(null); p.setStyle(Paint.Style.FILL); p.setColor(color);
            p.setTextSize(size); p.setTextAlign(a);
            p.setTypeface(Typeface.create("sans", bold ? Typeface.BOLD : Typeface.NORMAL));
            p.clearShadowLayer();
            c.drawText(s,x,y,p);
        }

        void neonText(Canvas c,String s,float x,float y,float size,int color,Paint.Align a){
            p.setStyle(Paint.Style.FILL); p.setTextSize(size); p.setTextAlign(a);
            p.setTypeface(Typeface.create("sans",Typeface.BOLD));
            p.setColor(Color.WHITE);
            p.setShadowLayer(18,0,0,color);
            c.drawText(s,x,y,p);
            p.clearShadowLayer();
        }

        void glowRect(Canvas c,float l,float t,float rr,float bb,int color){
            if(neon){
                p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(5); p.setColor(color);
                p.setShadowLayer(24,0,0,color); c.drawRoundRect(l,t,rr,bb,8,8,p);
                p.setShadowLayer(10,0,0,color); p.setStrokeWidth(2); c.drawRoundRect(l+3,t+3,rr-3,bb-3,6,6,p);
                p.clearShadowLayer();
            }
        }

        void button(Canvas c,float l,float t,float rr,float bb,String s,int color){
            p.setShader(new LinearGradient(l,t,rr,bb,
                    Color.rgb(35,38,52),Color.rgb(12,14,22),Shader.TileMode.CLAMP));
            p.setStyle(Paint.Style.FILL); c.drawRoundRect(l,t,rr,bb,8,8,p); p.setShader(null);
            p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(2); p.setColor(Color.argb(230,120,125,145));
            c.drawRoundRect(l,t,rr,bb,8,8,p);
            glowRect(c,l,t,rr,bb,color);
            neonText(c,s,(l+rr)/2,(t+bb)/2+10,28,color,Paint.Align.CENTER);
        }

        void background(Canvas c){
            p.setShader(new LinearGradient(0,0,0,691,Color.rgb(12,5,25),Color.rgb(2,7,14),Shader.TileMode.CLAMP));
            p.setStyle(Paint.Style.FILL); c.drawRect(0,0,1536,691,p); p.setShader(null);
            Random q=new Random(7);
            for(int i=0;i<70;i++){
                int x=q.nextInt(1536), y=70+q.nextInt(600), z=15+q.nextInt(100);
                int col=(i%3==0)?Color.rgb(70,20,125):(i%3==1?Color.rgb(10,100,145):Color.rgb(120,20,80));
                p.setColor(Color.argb(45,col>>16&255,col>>8&255,col&255));
                p.setStyle(Paint.Style.FILL); c.drawRect(x,y,x+z,y+z/2,p);
            }
            if(neon){
                p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(2); p.setColor(Color.argb(90,120,30,255));
                p.setShadowLayer(18,0,0,Color.rgb(130,40,255)); c.drawRect(8,8,1528,683,p); p.clearShadowLayer();
            }
        }

        @Override protected void onDraw(Canvas c){
            float W=getWidth(), H=getHeight(); sx=W/1536f; sy=H/691f;
            c.save(); c.scale(sx,sy); background(c);
            if(screen==0) home(c);
            else if(screen==1) start(c);
            else if(screen==2) settings(c);
            else if(screen==3) realms(c);
            else if(screen==4) wardrobe(c);
            else profile(c);
            c.restore();
        }

        void header(Canvas c,String title,String sub){
            neonText(c,title,768,95,44,Color.rgb(185,70,255),Paint.Align.CENTER);
            text(c,sub,768,130,22,Color.rgb(190,205,230),Paint.Align.CENTER,false);
        }

        void home(Canvas c){
            neonText(c,"ПОДПИШИСЬ НА КАНАЛ С РПО ОНИ",768,132,39,Color.rgb(80,220,255),Paint.Align.CENTER);
            neonText(c,"ВЫХОДЯТ КАЖДЫЙ ДЕНЬ",768,184,39,Color.rgb(185,70,255),Paint.Align.CENTER);
            neonText(c,"ТГК: @BEDROCKGO",768,236,39,Color.rgb(255,65,190),Paint.Align.CENTER);

            button(c,560,276,975,356,"Начать",Color.rgb(60,220,255));
            button(c,560,368,975,447,"Настройки",Color.rgb(180,70,255));
            button(c,560,459,975,538,"Realms",Color.rgb(255,65,190));

            button(c,1318,17,1518,80,"Соцсеть (0)",Color.rgb(60,220,255));
            button(c,1180,543,1332,611,"Гардеробная",Color.rgb(255,65,190));
            button(c,105,547,265,609,"Профиль",Color.rgb(180,70,255));

            p.setStyle(Paint.Style.FILL); p.setColor(Color.rgb(135,35,65));
            if(neon) p.setShadowLayer(22,0,0,Color.rgb(255,35,95));
            c.drawRect(1218,338,1292,475,p); c.drawRect(1203,306,1300,365,p);
            p.clearShadowLayer();
            p.setColor(Color.rgb(235,220,230)); c.drawRect(1220,362,1240,398,p); c.drawRect(1270,362,1290,398,p);

            button(c,610,545,790,626,"Рынок",Color.rgb(60,220,255));
            button(c,795,545,975,626,"Add-ons!",Color.rgb(255,210,60));
            text(c,"© Mojang AB",0,684,25,Color.WHITE,Paint.Align.LEFT,false);
            text(c,"v26.40",1515,684,25,Color.WHITE,Paint.Align.RIGHT,false);
        }

        void back(Canvas c){ button(c,45,35,230,100,"Назад",Color.rgb(60,220,255)); }

        void start(Canvas c){
            header(c,"ИГРАТЬ","Выбери действие");
            button(c,520,205,1016,285,"Создать мир",Color.rgb(60,220,255));
            button(c,520,305,1016,385,"Мои миры",Color.rgb(180,70,255));
            button(c,520,405,1016,485,"Подключиться",Color.rgb(255,65,190));
            back(c);
        }

        void settings(Canvas c){
            header(c,"НАСТРОЙКИ","Параметры интерфейса");
            button(c,520,190,1016,270,"Неон: "+(neon?"ВКЛ":"ВЫКЛ"),Color.rgb(180,70,255));
            button(c,520,290,1016,370,"Музыка: "+(music?"ВКЛ":"ВЫКЛ"),Color.rgb(60,220,255));
            button(c,520,390,1016,470,"Сбросить настройки",Color.rgb(255,65,190));
            back(c);
        }

        void realms(Canvas c){
            header(c,"REALMS","Сетевые миры");
            button(c,520,205,1016,285,"Мой Realm",Color.rgb(180,70,255));
            button(c,520,305,1016,385,"Присоединиться",Color.rgb(60,220,255));
            button(c,520,405,1016,485,"Обновить список",Color.rgb(255,65,190));
            back(c);
        }

        void wardrobe(Canvas c){
            header(c,"ГАРДЕРОБНАЯ","Настрой внешний вид");
            button(c,520,205,1016,285,"Красный стиль",Color.rgb(255,65,95));
            button(c,520,305,1016,385,"Неоновый стиль",Color.rgb(60,220,255));
            button(c,520,405,1016,485,"Фиолетовый стиль",Color.rgb(180,70,255));
            back(c);
        }

        void profile(Canvas c){
            header(c,"ПРОФИЛЬ","BedrockVisual");
            button(c,520,205,1016,285,"Изменить ник",Color.rgb(60,220,255));
            button(c,520,305,1016,385,"Статистика",Color.rgb(180,70,255));
            button(c,520,405,1016,485,"Выйти из профиля",Color.rgb(255,65,190));
            back(c);
        }

        @Override public boolean onTouchEvent(MotionEvent e){
            if(e.getAction()!=MotionEvent.ACTION_UP) return true;
            float x=e.getX()/sx, y=e.getY()/sy;

            if(screen==0){
                if(hit(x,y,560,276,975,356)){ screen=1; invalidate(); }
                else if(hit(x,y,560,368,975,447)){ screen=2; invalidate(); }
                else if(hit(x,y,560,459,975,538)){ screen=3; invalidate(); }
                else if(hit(x,y,1180,543,1332,611)){ screen=4; invalidate(); }
                else if(hit(x,y,105,547,265,609)){ screen=5; invalidate(); }
                else if(hit(x,y,1318,17,1518,80)) toast("Соцсеть пока не подключена");
                else if(hit(x,y,610,545,790,626)) toast("Рынок открыт");
                else if(hit(x,y,795,545,975,626)) toast("Раздел Add-ons открыт");
            } else if(hit(x,y,45,35,230,100)){
                screen=0; invalidate();
            } else if(screen==1){
                if(hit(x,y,520,205,1016,285)) toast("Новый мир создан");
                else if(hit(x,y,520,305,1016,385)) toast("Список миров пуст");
                else if(hit(x,y,520,405,1016,485)) toast("Подключение к серверу...");
            } else if(screen==2){
                if(hit(x,y,520,190,1016,270)){ neon=!neon; invalidate(); }
                else if(hit(x,y,520,290,1016,370)){ music=!music; toast("Музыка: "+(music?"ВКЛ":"ВЫКЛ")); invalidate(); }
                else if(hit(x,y,520,390,1016,470)){ neon=true; music=true; invalidate(); toast("Настройки сброшены"); }
            } else if(screen==3){
                if(hit(x,y,520,205,1016,285)) toast("Открыт мой Realm");
                else if(hit(x,y,520,305,1016,385)) toast("Введи код приглашения");
                else if(hit(x,y,520,405,1016,485)) toast("Список обновлён");
            } else if(screen==4){
                if(hit(x,y,520,205,1016,285)) toast("Красный стиль выбран");
                else if(hit(x,y,520,305,1016,385)){ neon=true; invalidate(); toast("Неоновый стиль выбран"); }
                else if(hit(x,y,520,405,1016,485)) toast("Фиолетовый стиль выбран");
            } else {
                if(hit(x,y,520,205,1016,285)) toast("Редактор ника открыт");
                else if(hit(x,y,520,305,1016,385)) toast("Статистика: 0 миров");
                else if(hit(x,y,520,405,1016,485)) toast("Выход выполнен");
            }
            return true;
        }

        boolean hit(float x,float y,float l,float t,float r,float b){
            return x>=l && x<=r && y>=t && y<=b;
        }

        void toast(String s){
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }
}
