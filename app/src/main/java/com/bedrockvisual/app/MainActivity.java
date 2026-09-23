package com.bedrockvisual.app;

import android.app.*;
import android.os.*;
import android.graphics.*;
import android.view.*;
import android.widget.Toast;
import java.util.*;

public class MainActivity extends Activity {
    MenuView menu;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        menu = new MenuView();
        setContentView(menu);
    }

    void openGame() { setContentView(new GameView()); }

    @Override public void onBackPressed() {
        if (!(findViewById(android.R.id.content).getChildAt(0) instanceof MenuView)) setContentView(menu);
        else super.onBackPressed();
    }

    class MenuView extends View {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        float sx=1, sy=1;
        int screen=0;
        boolean music=true, neon=true;

        MenuView() {
            super(MainActivity.this);
            setFocusable(true);
        }

        void text(Canvas c,String s,float x,float y,float size,int color,Paint.Align a,boolean bold){
            p.setShader(null); p.setStyle(Paint.Style.FILL); p.setColor(color);
            p.setTextSize(size); p.setTextAlign(a);
            p.setTypeface(Typeface.create("sans", bold ? Typeface.BOLD : Typeface.NORMAL));
            p.clearShadowLayer(); c.drawText(s,x,y,p);
        }

        void neonText(Canvas c,String s,float x,float y,float size,int color,Paint.Align a){
            p.setStyle(Paint.Style.FILL); p.setTextSize(size); p.setTextAlign(a);
            p.setTypeface(Typeface.create("sans",Typeface.BOLD)); p.setColor(Color.WHITE);
            p.setShadowLayer(18,0,0,color); c.drawText(s,x,y,p); p.clearShadowLayer();
        }

        void glowRect(Canvas c,float l,float t,float rr,float bb,int color){
            if(neon){
                p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(5); p.setColor(color);
                p.setShadowLayer(24,0,0,color); c.drawRoundRect(l,t,rr,bb,8,8,p);
                p.setShadowLayer(10,0,0,color); p.setStrokeWidth(2);
                c.drawRoundRect(l+3,t+3,rr-3,bb-3,6,6,p); p.clearShadowLayer();
            }
        }

        void button(Canvas c,float l,float t,float rr,float bb,String s,int color){
            p.setShader(new LinearGradient(l,t,rr,bb,Color.rgb(35,38,52),Color.rgb(12,14,22),Shader.TileMode.CLAMP));
            p.setStyle(Paint.Style.FILL); c.drawRoundRect(l,t,rr,bb,8,8,p); p.setShader(null);
            p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(2); p.setColor(Color.argb(230,120,125,145));
            c.drawRoundRect(l,t,rr,bb,8,8,p); glowRect(c,l,t,rr,bb,color);
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
                c.drawRect(x,y,x+z,y+z/2,p);
            }
            if(neon){
                p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(2); p.setColor(Color.argb(90,120,30,255));
                p.setShadowLayer(18,0,0,Color.rgb(130,40,255)); c.drawRect(8,8,1528,683,p); p.clearShadowLayer();
            }
        }

        @Override protected void onDraw(Canvas c){
            sx=getWidth()/1536f; sy=getHeight()/691f;
            c.save(); c.scale(sx,sy); background(c);
            if(screen==0) home(c); else if(screen==1) start(c); else if(screen==2) settings(c);
            else if(screen==3) realms(c); else if(screen==4) wardrobe(c); else profile(c);
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
            c.drawRect(1218,338,1292,475,p); c.drawRect(1203,306,1300,365,p); p.clearShadowLayer();
            p.setColor(Color.rgb(235,220,230)); c.drawRect(1220,362,1240,398,p); c.drawRect(1270,362,1290,398,p);
            button(c,610,545,790,626,"Рынок",Color.rgb(60,220,255));
            button(c,795,545,975,626,"Add-ons!",Color.rgb(255,210,60));
            text(c,"© BedrockVisual",0,684,25,Color.WHITE,Paint.Align.LEFT,false);
            text(c,"v26.40",1515,684,25,Color.WHITE,Paint.Align.RIGHT,false);
        }

        void back(Canvas c){ button(c,45,35,230,100,"Назад",Color.rgb(60,220,255)); }

        void start(Canvas c){
            header(c,"ИГРАТЬ","Небольшой voxel-движок прямо внутри приложения");
            button(c,520,205,1016,285,"Создать мир",Color.rgb(60,220,255));
            button(c,520,305,1016,385,"Мои миры",Color.rgb(180,70,255));
            button(c,520,405,1016,485,"Подключиться",Color.rgb(255,65,190)); back(c);
        }

        void settings(Canvas c){
            header(c,"НАСТРОЙКИ","Параметры интерфейса");
            button(c,520,190,1016,270,"Неон: "+(neon?"ВКЛ":"ВЫКЛ"),Color.rgb(180,70,255));
            button(c,520,290,1016,370,"Музыка: "+(music?"ВКЛ":"ВЫКЛ"),Color.rgb(60,220,255));
            button(c,520,390,1016,470,"Сбросить настройки",Color.rgb(255,65,190)); back(c);
        }

        void realms(Canvas c){
            header(c,"REALMS","Сетевые миры");
            button(c,520,205,1016,285,"Мой Realm",Color.rgb(180,70,255));
            button(c,520,305,1016,385,"Присоединиться",Color.rgb(60,220,255));
            button(c,520,405,1016,485,"Обновить список",Color.rgb(255,65,190)); back(c);
        }

        void wardrobe(Canvas c){
            header(c,"ГАРДЕРОБНАЯ","Настрой внешний вид");
            button(c,520,205,1016,285,"Красный стиль",Color.rgb(255,65,95));
            button(c,520,305,1016,385,"Неоновый стиль",Color.rgb(60,220,255));
            button(c,520,405,1016,485,"Фиолетовый стиль",Color.rgb(180,70,255)); back(c);
        }

        void profile(Canvas c){
            header(c,"ПРОФИЛЬ","BedrockVisual");
            button(c,520,205,1016,285,"Изменить ник",Color.rgb(60,220,255));
            button(c,520,305,1016,385,"Статистика",Color.rgb(180,70,255));
            button(c,520,405,1016,485,"Выйти из профиля",Color.rgb(255,65,190)); back(c);
        }

        @Override public boolean onTouchEvent(MotionEvent e){
            if(e.getAction()!=MotionEvent.ACTION_UP) return true;
            float x=e.getX()/sx, y=e.getY()/sy;
            if(screen==0){
                if(hit(x,y,560,276,975,356)){screen=1;invalidate();}
                else if(hit(x,y,560,368,975,447)){screen=2;invalidate();}
                else if(hit(x,y,560,459,975,538)){screen=3;invalidate();}
                else if(hit(x,y,1180,543,1332,611)){screen=4;invalidate();}
                else if(hit(x,y,105,547,265,609)){screen=5;invalidate();}
                else if(hit(x,y,1318,17,1518,80)) toast("Соцсеть пока не подключена");
                else if(hit(x,y,610,545,790,626)) toast("Рынок открыт");
                else if(hit(x,y,795,545,975,626)) toast("Раздел Add-ons открыт");
            } else if(hit(x,y,45,35,230,100)){screen=0;invalidate();}
            else if(screen==1){
                if(hit(x,y,520,205,1016,285)) openGame();
                else if(hit(x,y,520,305,1016,385)) toast("Список миров пуст");
                else if(hit(x,y,520,405,1016,485)) toast("Подключение к серверу...");
            } else if(screen==2){
                if(hit(x,y,520,190,1016,270)){neon=!neon;invalidate();}
                else if(hit(x,y,520,290,1016,370)){music=!music;toast("Музыка: "+(music?"ВКЛ":"ВЫКЛ"));invalidate();}
                else if(hit(x,y,520,390,1016,470)){neon=true;music=true;invalidate();toast("Настройки сброшены");}
            } else if(screen==3){
                if(hit(x,y,520,205,1016,285)) toast("Открыт мой Realm");
                else if(hit(x,y,520,305,1016,385)) toast("Введи код приглашения");
                else if(hit(x,y,520,405,1016,485)) toast("Список обновлён");
            } else if(screen==4){
                if(hit(x,y,520,205,1016,285)) toast("Красный стиль выбран");
                else if(hit(x,y,520,305,1016,385)){neon=true;invalidate();toast("Неоновый стиль выбран");}
                else if(hit(x,y,520,405,1016,485)) toast("Фиолетовый стиль выбран");
            } else {
                if(hit(x,y,520,205,1016,285)) toast("Редактор ника открыт");
                else if(hit(x,y,520,305,1016,385)) toast("Статистика: 0 миров");
                else if(hit(x,y,520,405,1016,485)) toast("Выход выполнен");
            }
            return true;
        }

        boolean hit(float x,float y,float l,float t,float r,float b){return x>=l&&x<=r&&y>=t&&y<=b;}
        void toast(String s){Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();}
    }

    class GameView extends View {
        final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        final int SX=28,SZ=28,SY=10;
        final byte[][][] world = new byte[SX][SY][SZ];
        final ArrayList<Face> faces = new ArrayList<>();
        final float[] v = new float[3];
        float px=14.5f,pz=14.5f,py=5f,vy=0;
        float yaw=0.65f,pitch=-0.18f;
        float lastX,lastY; boolean looking=false;
        boolean up,down,left,right,jump;
        long lastFrame=System.nanoTime();
        int selected=1;

        class Face {
            Path path; float depth; int color;
            Face(Path a,float d,int col){path=a;depth=d;color=col;}
        }

        GameView() {
            super(MainActivity.this);
            setFocusable(true);
            generateWorld();
            postInvalidateOnAnimation();
        }

        void generateWorld() {
            Random r=new Random(42);
            for(int x=0;x<SX;x++) for(int z=0;z<SZ;z++) {
                int h=2+(int)(1.5f+Math.sin(x*.45)*.8f+Math.cos(z*.38)*.7f);
                h=Math.max(1,Math.min(5,h));
                for(int y=0;y<=h;y++) world[x][y][z]=(byte)(y==h?1:2);
                if(r.nextFloat()<.045f && h<7 && x>2&&z>2&&x<SX-3&&z<SZ-3) {
                    for(int y=h+1;y<=h+3;y++) world[x][y][z]=3;
                    for(int ox=-1;ox<=1;ox++) for(int oz=-1;oz<=1;oz++) for(int oy=2;oy<=4;oy++)
                        if(Math.abs(ox)+Math.abs(oz)<3 && h+oy<SY) world[x+ox][h+oy][z+oz]=4;
                }
            }
        }

        int color(byte b, int face) {
            if(b==1) return face==0?Color.rgb(74,190,72):Color.rgb(115,78,45);
            if(b==2) return Color.rgb(120,78,46);
            if(b==3) return Color.rgb(150,95,48);
            if(b==4) return Color.rgb(48,150,62);
            return Color.GRAY;
        }

        float[] project(float x,float y,float z,float W,float H) {
            float dx=x-px,dz=z-pz,dy=y-(py+1.55f);
            float cy=(float)Math.cos(yaw), sy=(float)Math.sin(yaw);
            float rx=cy*dx-sy*dz, rz=sy*dx+cy*dz;
            float cp=(float)Math.cos(pitch), sp=(float)Math.sin(pitch);
            float ry=cp*dy-sp*rz, zz=sp*dy+cp*rz;
            if(zz<0.15f) return null;
            float f=Math.min(W,H)*.72f;
            return new float[]{W*.5f+rx*f/zz,H*.48f-ry*f/zz,zz};
        }

        void addFace(Canvas c,float[][] pts,int col,float depth) {
            Path path=new Path(); boolean ok=true;
            for(int i=0;i<4;i++){
                float[] q=project(pts[i][0],pts[i][1],pts[i][2],getWidth(),getHeight());
                if(q==null){ok=false;break;}
                if(i==0) path.moveTo(q[0],q[1]); else path.lineTo(q[0],q[1]);
            }
            if(ok){path.close();faces.add(new Face(path,depth,col));}
        }

        void addCubeFaces(int x,int y,int z,byte b) {
            float X=x,Y=y,Z=z;
            if(y+1>=SY || world[x][y+1][z]==0)
                addFace(null,new float[][]{{X,Y+1,Z},{X+1,Y+1,Z},{X+1,Y+1,Z+1},{X,Y+1,Z+1}},color(b,0),depth(X+.5f,Y+1,Z+.5f));
            if(y-1<0 || world[x][y-1][z]==0)
                addFace(null,new float[][]{{X,Y,Z+1},{X+1,Y,Z+1},{X+1,Y,Z},{X,Y,Z}},color(b,1),depth(X+.5f,Y,Z+.5f));
            if(x+1>=SX || world[x+1][y][z]==0)
                addFace(null,new float[][]{{X+1,Y,Z+1},{X+1,Y+1,Z+1},{X+1,Y+1,Z},{X+1,Y,Z}},color(b,2),depth(X+1,Y+.5f,Z+.5f));
            if(x-1<0 || world[x-1][y][z]==0)
                addFace(null,new float[][]{{X,Y,Z},{X,Y+1,Z},{X,Y+1,Z+1},{X,Y,Z+1}},color(b,2)-0x202020,depth(X,Y+.5f,Z+.5f));
            if(z+1>=SZ || world[x][y][z+1]==0)
                addFace(null,new float[][]{{X,Y,Z+1},{X,Y+1,Z+1},{X+1,Y+1,Z+1},{X+1,Y,Z+1}},color(b,2)+0x101010,depth(X+.5f,Y+.5f,Z+1));
            if(z-1<0 || world[x][y][z-1]==0)
                addFace(null,new float[][]{{X+1,Y,Z},{X+1,Y+1,Z},{X,Y+1,Z},{X,Y,Z}},color(b,2)-0x303030,depth(X+.5f,Y+.5f,Z));
        }

        float depth(float x,float y,float z) {
            float dx=x-px,dz=z-pz;
            float cy=(float)Math.cos(yaw),sy=(float)Math.sin(yaw);
            return sy*dx+cy*dz;
        }

        @Override protected void onDraw(Canvas c) {
            super.onDraw(c);
            long now=System.nanoTime(); float dt=Math.min(.045f,(now-lastFrame)/1e9f); lastFrame=now;
            update(dt);
            drawSky(c);
            faces.clear();
            int minX=Math.max(0,(int)px-12),maxX=Math.min(SX-1,(int)px+12);
            int minZ=Math.max(0,(int)pz-12),maxZ=Math.min(SZ-1,(int)pz+12);
            for(int x=minX;x<=maxX;x++) for(int y=0;y<SY;y++) for(int z=minZ;z<=maxZ;z++)
                if(world[x][y][z]!=0) addCubeFaces(x,y,z,world[x][y][z]);
            Collections.sort(faces,new Comparator<Face>(){public int compare(Face a,Face b){return Float.compare(b.depth,a.depth);}});
            for(Face f:faces){p.setStyle(Paint.Style.FILL);p.setColor(f.color);c.drawPath(f.path,p);p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(0.8f);p.setColor(Color.argb(70,0,0,0));c.drawPath(f.path,p);}
            drawHud(c);
            postInvalidateOnAnimation();
        }

        void drawSky(Canvas c) {
            p.setStyle(Paint.Style.FILL);
            p.setShader(new LinearGradient(0,0,0,getHeight(),Color.rgb(40,125,205),Color.rgb(170,220,250),Shader.TileMode.CLAMP));
            c.drawRect(0,0,getWidth(),getHeight(),p); p.setShader(null);
            p.setColor(Color.argb(120,255,255,210)); c.drawCircle(getWidth()-170,110,55,p);
        }

        void drawHud(Canvas c) {
            float W=getWidth(),H=getHeight();
            p.setColor(Color.argb(85,0,0,0));p.setStyle(Paint.Style.FILL);
            c.drawRect(0,0,W,55,p);
            text(c,"BEDROCKVISUAL  •  VOXEL ENGINE",18,34,22,Color.WHITE,Paint.Align.LEFT,true);
            text(c,"Блок: "+selected+"   X:"+String.format(Locale.US,"%.1f",px)+" Z:"+String.format(Locale.US,"%.1f",pz),W-18,34,18,Color.WHITE,Paint.Align.RIGHT,false);
            p.setColor(Color.WHITE);p.setStrokeWidth(2);c.drawLine(W/2-10,H/2,W/2+10,H/2,p);c.drawLine(W/2,H/2-10,W/2,H/2+10,p);

            control(c,70,H-130,150,H-50,"W"); control(c,70,H-50,150,H+30,"S");
            control(c,0,H-90,80,H-10,"A"); control(c,140,H-90,220,H-10,"D");
            control(c,W-170,H-130,W-50,H-50,"JUMP");
            control(c,W-170,H-65,W-50,H+15,"BLOCK");

            for(int i=0;i<4;i++){
                float l=W/2-130+i*68; p.setColor(i+1==selected?Color.rgb(220,220,80):Color.argb(150,20,20,25));
                p.setStyle(Paint.Style.FILL);c.drawRect(l,H-58,l+58,H-6,p);
                p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(2);p.setColor(Color.WHITE);c.drawRect(l,H-58,l+58,H-6,p);
                text(c,""+(i+1),l+29,H-22,18,Color.WHITE,Paint.Align.CENTER,true);
            }
            text(c,"ЛКМ/тап: убрать блок • BLOCK: поставить блок • свайп справа: обзор • BACK: меню",W/2,H-70,16,Color.WHITE,Paint.Align.CENTER,false);
            p.setStyle(Paint.Style.FILL);p.setColor(Color.argb(130,0,0,0));c.drawRoundRect(18,65,118,112,8,8,p);
            text(c,"BACK",68,96,18,Color.WHITE,Paint.Align.CENTER,true);
        }

        void control(Canvas c,float l,float t,float r,float b,String s){
            p.setColor(Color.argb(120,10,10,18));p.setStyle(Paint.Style.FILL);c.drawRoundRect(l,t,r,b,16,16,p);
            p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(2);p.setColor(Color.argb(180,100,230,255));c.drawRoundRect(l,t,r,b,16,16,p);
            text(c,s,(l+r)/2,(t+b)/2+7,17,Color.WHITE,Paint.Align.CENTER,true);
        }

        void update(float dt) {
            float speed=4.2f*dt;
            float fx=(float)Math.sin(yaw), fz=(float)Math.cos(yaw);
            float rx=(float)Math.cos(yaw), rz=-(float)Math.sin(yaw);
            if(up){px+=fx*speed;pz+=fz*speed;} if(down){px-=fx*speed;pz-=fz*speed;}
            if(left){px-=rx*speed;pz-=rz*speed;} if(right){px+=rx*speed;pz+=rz*speed;}
            px=Math.max(1.2f,Math.min(SX-1.2f,px));pz=Math.max(1.2f,Math.min(SZ-1.2f,pz));
            float ground=groundY(px,pz)+1f;
            if(jump && py<=ground+.08f) vy=6f;
            vy-=15f*dt; py+=vy*dt;
            if(py<ground){py=ground;vy=0;}
        }

        float groundY(float x,float z){
            int ix=Math.max(0,Math.min(SX-1,(int)x)),iz=Math.max(0,Math.min(SZ-1,(int)z));
            for(int y=SY-1;y>=0;y--) if(world[ix][y][iz]!=0) return y+1;
            return 1;
        }

        void actionBlock(boolean place){
            float fx=(float)(Math.sin(yaw)*Math.cos(pitch)), fy=(float)Math.sin(pitch), fz=(float)(Math.cos(yaw)*Math.cos(pitch));
            int lastX=-1,lastY=-1,lastZ=-1;
            for(float d=.35f;d<7f;d+=.12f){
                int x=(int)Math.floor(px+fx*d), y=(int)Math.floor(py+1.55f+fy*d), z=(int)Math.floor(pz+fz*d);
                if(x<0||x>=SX||y<0||y>=SY||z<0||z>=SZ) continue;
                if(world[x][y][z]!=0){
                    if(place && lastX>=0) world[lastX][lastY][lastZ]=(byte)selected;
                    else if(!place) world[x][y][z]=0;
                    return;
                }
                lastX=x;lastY=y;lastZ=z;
            }
        }

        @Override public boolean onTouchEvent(MotionEvent e) {
            float x=e.getX(),y=e.getY(),W=getWidth(),H=getHeight();
            if(e.getAction()==MotionEvent.ACTION_DOWN){
                lastX=x;lastY=y;
                if(y<125 && x<145){setContentView(menu);return true;}
                if(y>H-150 && x<230){setMove(x,y,W,H,true);return true;}
                if(y>H-150 && x>W-190){if(y>H-90) actionBlock(true); else jump=true;return true;}
                if(y>H-75 && y<H-5 && x>W/2-140 && x<W/2+140){int i=(int)((x-(W/2-130))/68);if(i>=0&&i<4)selected=i+1;invalidate();return true;}
                looking=x>W*.32f;
                return true;
            }
            if(e.getAction()==MotionEvent.ACTION_MOVE){
                if(looking){
                    float dx=x-lastX,dy=y-lastY;
                    yaw+=dx*.006f; pitch-=dy*.004f; pitch=Math.max(-1.05f,Math.min(.85f,pitch));
                    lastX=x;lastY=y;invalidate();
                }
                return true;
            }
            if(e.getAction()==MotionEvent.ACTION_UP || e.getAction()==MotionEvent.ACTION_CANCEL){
                if(looking && Math.abs(x-lastX)<8 && Math.abs(y-lastY)<8 && x>W*.32f) actionBlock(false);
                up=down=left=right=false;jump=false;looking=false;
                return true;
            }
            return true;
        }

        void setMove(float x,float y,float W,float H,boolean on){
            if(y>H-130 && x>55&&x<165) up=true;
            else if(y>H-65 && x>55&&x<165) down=true;
            else if(y>H-100 && x<90) left=true;
            else if(y>H-100 && x>135&&x<225) right=true;
        }

        void text(Canvas c,String s,float x,float y,float size,int color,Paint.Align a,boolean bold){
            p.setStyle(Paint.Style.FILL);p.setColor(color);p.setTextSize(size);p.setTextAlign(a);
            p.setTypeface(Typeface.create("sans",bold?Typeface.BOLD:Typeface.NORMAL));p.clearShadowLayer();c.drawText(s,x,y,p);
        }
    }
}
