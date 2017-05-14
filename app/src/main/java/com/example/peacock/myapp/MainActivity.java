package com.example.peacock.myapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public SQLiteDBHelper SQLITEHELPER;
    public ListView LISTVIEW;
    int ID;
    ArrayList<Contact> userinfo;

    private Activity activity;
    private AlertDialog.Builder build, build1, build2;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userinfo = new ArrayList<>();

        final CharSequence[] items = {"Update Data", "Delete Record from List"};

        activity = MainActivity.this;

        SQLITEHELPER = new SQLiteDBHelper(this);

        //ListView
        LISTVIEW = (ListView) findViewById(R.id.listView1);

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                final Contact contact = userinfo.get(i);
                ID = userinfo.get(i).get_id();

                build = new AlertDialog.Builder(activity);

                build.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            build2 = new AlertDialog.Builder(activity);
                            build2.setTitle("Upadte the Data");
                            build2.setMessage("Are you sure to Update record?");
                            build2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(activity, UserDetailActivity.class);

                                    intent.putExtra("Id", ID);
                                    intent.putExtra("Uname", contact.getName());
                                    intent.putExtra("Udob", contact.getDob());
                                    intent.putExtra("Uphoto", contact.get_photo());
                                    intent.putExtra("Ugender", contact.get_gender());
                                    intent.putExtra("Ucountry", contact.get_country());
                                    intent.putExtra("Uhobbies", contact.get_hobbies());

                                    intent.putExtra("update", true);
                                    startActivity(intent);

                                    finish();
                                    Displaynfo();
                                    dialogInterface.cancel();
                                }
                            });
                            build2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            build2.show();
                        } else {
                            build1 = new AlertDialog.Builder(activity);

                            build1.setTitle("Remove Record !!!");
                            build1.setMessage("Are you sure remove the Information");
                            build1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SQLITEHELPER.Deleteuser(contact);
                                    Toast.makeText(getApplicationContext(), "Data Remove", Toast.LENGTH_SHORT).show();

                                    Displaynfo();
                                    dialogInterface.cancel();
                                }
                            });
                            build1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            build1.show();
                        }
                    }
                });
                build.create().show();
//                return true;
            }
        });

        LISTVIEW.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Contact contact = userinfo.get(i);
                ID = userinfo.get(i).get_id();

                build = new AlertDialog.Builder(activity);

                build.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            build2 = new AlertDialog.Builder(activity);
                            build2.setTitle("Upadte the Data");
                            build2.setMessage("Are you sure to Update record?");
                            build2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(activity, UserDetailActivity.class);

                                    intent.putExtra("Id", ID);
                                    intent.putExtra("Uname", contact.getName());
                                    intent.putExtra("Udob", contact.getDob());
                                    intent.putExtra("Uphoto", contact.get_photo());
                                    intent.putExtra("Ugender", contact.get_gender());
                                    intent.putExtra("Ucountry", contact.get_country());
                                    intent.putExtra("Uhobbies", contact.get_hobbies());

                                    intent.putExtra("update", true);
                                    startActivity(intent);

                                    finish();
                                    Displaynfo();
                                    dialogInterface.cancel();
                                }
                            });
                            build2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            build2.show();
                        } else {
                            build1 = new AlertDialog.Builder(activity);

                            build1.setTitle("Remove Record !!!");
                            build1.setMessage("Are you sure remove the Information");
                            build1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SQLITEHELPER.Deleteuser(contact);
                                    Toast.makeText(getApplicationContext(), "Data Remove", Toast.LENGTH_SHORT).show();

                                    Displaynfo();
                                    dialogInterface.cancel();
                                }
                            });
                            build1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            build1.show();
                        }
                    }
                });
                build.create().show();
                return true;
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Displaynfo();
    }

    public void Displaynfo() {
        LISTVIEW.setDivider(null);
        LISTVIEW.setDividerHeight(0);
        userinfo = SQLITEHELPER.getalluserinfo();
        if (userinfo != null && userinfo.size() > 0) {

            SQLiteListBaseAdapter adapter = new SQLiteListBaseAdapter(getApplicationContext(), userinfo);
            LISTVIEW.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            findViewById(R.id.rv_no_data_found).setVisibility(View.GONE);// NO student Data

        } else {

            LISTVIEW.setVisibility(View.GONE);// List view Gone
            findViewById(R.id.rv_no_data_found).setVisibility(View.VISIBLE);// NO student Data Visible

        }
    }


}
