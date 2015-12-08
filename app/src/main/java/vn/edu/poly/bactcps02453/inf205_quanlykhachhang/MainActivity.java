package vn.edu.poly.bactcps02453.inf205_quanlykhachhang;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.GetCallback;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText msKH;
    EditText tenKH;
    EditText sdtKH;
    Button btThem;
    Button btSua;
    ListView lvKH;
    Button btLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        laydulieu();
        msKH = (EditText)findViewById(R.id.edtMSKH);
        tenKH = (EditText)findViewById(R.id.edtTenKH);
        sdtKH = (EditText)findViewById(R.id.edtSDT);
        btThem = (Button)findViewById(R.id.btThem);
        btSua = (Button)findViewById(R.id.btSua);
        lvKH = (ListView)findViewById(R.id.lvKH);
        btLoad = (Button)findViewById(R.id.btLoad);

        btLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laydulieu();
                Toast.makeText(MainActivity.this,"Lay du lieu tu may chu thanh cong!",Toast.LENGTH_LONG).show();
            }
        });

        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ms = msKH.getText().toString();
                String ten = tenKH.getText().toString();
                String sdt = sdtKH.getText().toString();

                ParseObject qlkh = new ParseObject("INF205_QuanLyKhachHang");
                qlkh.put("MaSoKH", ms);
                qlkh.put("TenKH", ten);
                qlkh.put("SoDienThoai", sdt);
                qlkh.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        laydulieu();
                    }
                });


            }
        });
        btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ms = msKH.getText().toString();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("INF205_QuanLyKhachHang");
                query.whereEqualTo("MaSoKH",ms);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {

                            String ten = tenKH.getText().toString();
                            String sdt = sdtKH.getText().toString();
                            object.put("TenKH", ten);
                            object.put("SoDienThoai", sdt);
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    laydulieu();
                                    Toast.makeText(MainActivity.this,"Sua thanh cong",Toast.LENGTH_LONG).show();

                                }
                            });


                        }
                    }
                });
            }
        });
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "RnHNRcQcMzZng3mApWl8gKwOmYBJIfK8XB1qHHnK", "cjPgzF0EabL6qsZqXqslzjR72QjJYIZK778nQLeZ");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void laydulieu(){
        final ArrayList<String> mangKH = new ArrayList<String>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("INF205_QuanLyKhachHang");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    for (ParseObject kh : scoreList) {
                        mangKH.add(kh.getString("MaSoKH")+"\t"+kh.getString("TenKH")+"\t"+kh.getString("SoDienThoai"));
                    }
                    Toast.makeText(MainActivity.this," "+scoreList.size(),Toast.LENGTH_LONG).show();
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,mangKH);
                    lvKH.setAdapter(adapter);

                } else {

                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
