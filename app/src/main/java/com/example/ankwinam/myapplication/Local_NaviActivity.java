package com.example.ankwinam.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by An Kwi nam on 2016-09-08.
 */
public class Local_NaviActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<HashMap<String,String>> famousList;
    private ListView lv;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.local_navigation);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            //지역별 스크롤 스피너 매핑 부분
            String[] localspnnier = getResources().getStringArray(R.array.Localspnnier);
            ArrayAdapter<String> local_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, localspnnier);
            Spinner spnnier = (Spinner) findViewById(R.id.local_spinner);
            spnnier.setAdapter(local_adapter);

            spnnier.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected
                                (AdapterView<?> parent, View view, int position, long id) {
                            print(view, position); //아이템을 선택하면 print가 실행된다 (이 부분 수정해서 리스트 보여주기)
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    }
            );

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);


            //Json 처리
            //공공데이터 관광명소 Json 불어오기
            famousList = new ArrayList<>();
            lv = (ListView)findViewById(R.id.listView_famous);
            String data = loadJSONFromAsset("json/data.json");
            //Json 정보 저장장
            try {
               JSONObject d = new JSONObject(data);
                JSONArray famous = d.getJSONArray("DATA");



                for(int i=0; i<famous.length(); i++){
                    JSONObject c = famous.getJSONObject(i);

                    String name = c.getString("FAC_NAME");
                    String etc = c.getString("ETC_DESC");
                    String phone = c.getString("PHNE");
                    String img = c.getString("MAIN_IMG");
                    String homepage = c.getString("HOMEPAGE");
                    String adress = c.getString("ADDR");

                    HashMap<String, String> famous_data = new HashMap<>();
                    famous_data.put("name",name);
                    famous_data.put("etc",etc);
                    famous_data.put("phone",phone);
                    famous_data.put("img",img);
                    famous_data.put("homepage",homepage);
                    famous_data.put("adress",adress);

                    famousList.add(famous_data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter adapter = new SimpleAdapter(
                    Local_NaviActivity.this, famousList,
                    R.layout.local_info, new String[]{"name","adress","phone"},
                    new int[]{R.id.name, R.id.email, R.id.mobile}
            );
            lv.setAdapter(adapter);
        }

//수정해야할 print 함수 부분
    public void print(View v, int position){
        Spinner sp = (Spinner)findViewById(R.id.local_spinner);
        String res = "";
        if(sp.getSelectedItemPosition()>0){
            res=(String)sp.getAdapter().getItem(sp.getSelectedItemPosition());
        }
        if(res!=""){
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
        }
    }

    //Json파일 불러오는 Method
    public String loadJSONFromAsset(String url) {
        String json = null;
        try {
            InputStream is = getAssets().open(url);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
//오른쪽 옵션메뉴
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_local) {
//            Intent gotolocal = new Intent(Local_NaviActivity.this, Tema_NaviActivity.class);
//            startActivity(gotolocal);
//            finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

            // Handle the camera action
        if (id == R.id.menu_home) {
            Toast.makeText(getApplicationContext(), "홈", Toast.LENGTH_SHORT).show();
            Intent go_home = new Intent(Local_NaviActivity.this, Choice_NaviActivity.class);
            startActivity(go_home);
            finish();
        } else if (id == R.id.menu_local) {
            Toast.makeText(getApplicationContext(), "지역 별 이동", Toast.LENGTH_SHORT).show();
            Intent go_local = new Intent(Local_NaviActivity.this, Local_NaviActivity.class);
            startActivity(go_local);
            finish();
        } else if (id == R.id.menu_tema) {
            Toast.makeText(getApplicationContext(), "테마 별 이동", Toast.LENGTH_SHORT).show();
            Intent go_tema = new Intent(Local_NaviActivity.this, Tema_NaviActivity.class);
            startActivity(go_tema);
            finish();
        } else if (id == R.id.menu_history) {
            Toast.makeText(getApplicationContext(), "내가 쓴 글", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_stamp) {
            Toast.makeText(getApplicationContext(), "스탬프", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_jjim) {
            Toast.makeText(getApplicationContext(),"찜 한 산책로",Toast.LENGTH_SHORT).show();
            Intent go_jjim = new Intent(Local_NaviActivity.this, JJim_NaviActivity.class);
            startActivity(go_jjim);
            finish();
        } else if (id == R.id.menu_map) {
            Intent go_main = new Intent(Local_NaviActivity.this, MainActivity.class);
            startActivity(go_main);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
