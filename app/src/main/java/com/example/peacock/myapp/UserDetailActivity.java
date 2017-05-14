package com.example.peacock.myapp;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int Date_Dialog_ID = 0, CAMERA_REQUEST = 1888, PICK_IMAGE_REQUEST = 1;

    public int myear, mmonth, mday, YearSelected, MonthSelected, DaySelected;
    int Id;
    SQLiteDBHelper openHelper;
    String array[] = null;
    String str_fullname, str_dob, str_gender, str_country, str_hobbies,
            uhobbies = "", ucountry = "", ugender = "", Hobbies = "", Gender = "", uname = "", udob = "";
    String Hobbies_array[] = null;
    private Boolean CheckEditTextEmpty, isupdate = false;
    private byte[] bytes;
    private Bitmap bitmap = null;

    private PopupWindow mPopupWindow;
    private Context mContext;
    private EditText Name, edit_date;
    private Spinner spinner;
    private CheckBox ch1, ch2, ch3, ch4, ch5, ch6;
    private ImageView btn_Date, plus, Main_image;
    private RadioGroup radio_group;
    private RadioButton ch_b_1, ch_b_2;
    private Button Save, cancel, btn_showdata;
    private Activity activity;
    private SharedPreferences preferences = null;

    // Constructor
    // Register  DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            YearSelected = i;
            MonthSelected = i1;
            DaySelected = i2;
            edit_date.setText(" " + DaySelected + " " + "/" + " " + MonthSelected + " " + "/" + " " + YearSelected);
        }
    };

    public UserDetailActivity() {
        final Calendar cal = Calendar.getInstance();
        myear = cal.get(Calendar.YEAR);
        mmonth = cal.get(Calendar.MONTH);
        mday = cal.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // inside Intent is select image from Gallery.
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();/// inside Uri is store image Path
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Main_image.setImageBitmap(bitmap);//bitmap  is inside image...

                //Bitmap image convert to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                bytes = stream.toByteArray();
                System.out.println("");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //  inside Intent is select image from Camera.
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Main_image.setImageBitmap(photo);

            //Bitmap image convert to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bytes = stream.toByteArray();
            System.out.println("");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        activity = UserDetailActivity.this;

        openHelper = new SQLiteDBHelper(this);

        preferences = getSharedPreferences("storage", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("read", "");
        editor.putString("write", "");
        editor.putString("camera", "");
        editor.apply();

        // Get the application context
        mContext = getApplicationContext();

        Name = (EditText) findViewById(R.id.name);
        edit_date = (EditText) findViewById(R.id.Date_Bith);
        edit_date.setInputType(InputType.TYPE_NULL);// keyboard hide



        Main_image = (ImageView) findViewById(R.id.main_image);
        plus = (ImageView) findViewById(R.id.plus);

        Save = (Button) findViewById(R.id.btn_save);
        cancel = (Button) findViewById(R.id.btn_cancel);

        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        ch_b_1 = (RadioButton) findViewById(R.id.check_male);
        ch_b_2 = (RadioButton) findViewById(R.id.check_female);

        ch1 = (CheckBox) findViewById(R.id.checkbox1);
        ch2 = (CheckBox) findViewById(R.id.checkbox2);
        ch3 = (CheckBox) findViewById(R.id.checkbox3);
        ch4 = (CheckBox) findViewById(R.id.checkbox4);
        ch5 = (CheckBox) findViewById(R.id.checkbox5);
        ch6 = (CheckBox) findViewById(R.id.checkbox6);
        array = new String[]{"Malaysia", "United States", "Indonesia",
                "France", "Italy", "Singapore", "New Zealand", "India"};

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            isupdate = intent.getBoolean("update");

            if (isupdate) {

                Id = intent.getInt("Id");
                uname = intent.getString("Uname");
                udob = intent.getString("Udob");

                byte[] uphoto = getIntent().getByteArrayExtra("Uphoto");
                ugender = getIntent().getStringExtra("Ugender");
                ucountry = getIntent().getStringExtra("Ucountry");
                uhobbies = getIntent().getStringExtra("Uhobbies");

                Name.setText(uname);
                edit_date.setText(udob);

                if (uphoto != null) {
                    // this image is get by listview in imageview that is Byte format so then convert into Bitmap
                    //  Image is Converting Byte  to Bitmap
                    bitmap = BitmapFactory.decodeByteArray(uphoto, 0, uphoto.length);
                    Main_image.setImageBitmap(bitmap); // set image position to main_image
                }

            }
        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name.setText("");
                edit_date.setText("");
                Main_image.clearColorFilter();
                ch1.setChecked(false);
                ch2.setChecked(false);
                ch3.setChecked(false);
                ch4.setChecked(false);
                ch5.setChecked(false);
                ch6.setChecked(false);
                Main_image.setImageBitmap(null);
            }
        });

        // Set ClickListener on btnSelectDate
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(Date_Dialog_ID);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow(v);
            }
        });

        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.country_arrays, android.R.layout.simple_spinner_item);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);


        //  inside array is  in list of Country.....
        for (int i = 0; i < array.length; i++) {
            if (ucountry.equals(array[i])) {
                spinner.setSelection(i);
            }
        }
        //  it is decide previous position of Gender
        if (ugender.equals("Male")) {
            ch_b_1.setChecked(true);
        } else {
            ch_b_2.setChecked(true);

        }

        //   it is total hobbies include in uhobbies then this String to split here.
        //   split by .split method
        Hobbies_array = uhobbies.split(",");

        // it is decide previous position of checkBox in Hobbies..
        for (int j = 0; j < Hobbies_array.length; j++) {
            if (ch1.getText().toString().equals(Hobbies_array[j]))
                ch1.setChecked(true);
            if (ch2.getText().toString().equals(Hobbies_array[j]))
                ch2.setChecked(true);
            if (ch3.getText().toString().equals(Hobbies_array[j]))
                ch3.setChecked(true);
            if (ch4.getText().toString().equals(Hobbies_array[j]))
                ch4.setChecked(true);
            if (ch5.getText().toString().equals(Hobbies_array[j]))
                ch5.setChecked(true);
            if (ch6.getText().toString().equals(Hobbies_array[j]))
                ch6.setChecked(true);
        }


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // First checkBox Condition
                if (ch1.isChecked()) {
                    Hobbies = ch1.getText().toString();
                }
                // Another CheckBox Condition
                getHobbies(ch2);
                getHobbies(ch3);
                getHobbies(ch4);
                getHobbies(ch5);
                getHobbies(ch6);

                // Which Gender(RadioButton) is Selected in RadioGroup
                Gender = ((RadioButton) findViewById(radio_group.getCheckedRadioButtonId())).
                        getText().toString();

                //  Toast.makeText(mContext, Hobbies +"\n\n" + Gender, Toast.LENGTH_SHORT).show();

                str_fullname = Name.getText().toString().trim();
                str_dob = edit_date.getText().toString().trim();
                str_gender = Gender;
                str_hobbies = Hobbies;

                //  Upadte the the data  into Contact Class..
                Contact U = new Contact(Id, str_fullname, str_dob, str_gender, bytes, str_country, str_hobbies);
                if (isupdate) {
                    // Upadte the the data in SQLitedataBase
                    openHelper.Updateuser(U);
                    if (U != null) {
                        Toast.makeText(getApplicationContext(), "Data has been Updated !..", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(UserDetailActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                } else {

                    // Upadte the the data in SQLitedataBase
                    Contact user = new Contact(str_fullname, str_dob, str_gender, bytes, str_country, str_hobbies);
                    openHelper.adduser(user);
                    if (user != null) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(UserDetailActivity.this);
                        builder.setTitle("Information");
                        builder.setMessage("Your Account is Successfully Created.");
                        builder.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Finishing the dialog and removing Activity from stack.
                                dialogInterface.dismiss();
                                finish();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Toast.makeText(getApplicationContext(), "Data has been inserted !..", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UserDetailActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

        // show the data inside this Button
        btn_showdata = (Button) findViewById(R.id.btn_show);
        btn_showdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(String Name, String dob, String gender, String conutry, String hobbies) {

        if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(conutry) || TextUtils.isEmpty(hobbies)) {

            CheckEditTextEmpty = false;

        } else {
            CheckEditTextEmpty = true;
        }
    }


    // This method is check which CheckBox are selected.
    public void getHobbies(CheckBox chk) {

        if (chk.isChecked()) {
            if (Hobbies.isEmpty()) {
                Hobbies = chk.getText().toString();
            } else {

                Hobbies = Hobbies.concat(",").concat(chk.getText().toString());

            }
        }
    }

    //This method is used for Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        str_country = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    //This method also is used for Spinner
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //  This method is used for DatePicker
    @Override
    protected Dialog onCreateDialog(int id) {
        // create a new DatePickerDialog with values you want to show
        return new DatePickerDialog(this, mDateSetListener, myear, mmonth, mday);
    }

    //This method is used for Popup-Window
    private void initiatePopupWindow(View v) {

        if (preferences.getString("read", "").equals("") && preferences.getString("write", "").
                equals("") && preferences.getString("camera", "").equals("")) {

            checkPermission(); // this check the Read & Write permission used for Camera and Gallery..

        } else {

            showPopup();    // Show the PopupWindow

        }
    }

    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.
                    WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.
                    READ_EXTERNAL_STORAGE);
            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            } else {

                editSharedPreferences("write", "true");

            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {

                editSharedPreferences("read", "true");

            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.CAMERA);

            } else {

                editSharedPreferences("camera", "true");

            }

            if (!permissions.isEmpty()) {

                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);

            }
        } else {

            showPopup();

        }
    }

    private void editSharedPreferences(String tag, String value) {

        SharedPreferences.Editor editPermissions = preferences.edit();
        editPermissions.putString(tag, value);
        editPermissions.apply();

    }

    private void showPopup() {

        int[] array = new int[2];
        plus.getLocationOnScreen(array);

        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

     /*   LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(UserDetailActivity.this);
        linearLayoutCompat.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.
                LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));*/

        LinearLayout ll_mainLayout = (LinearLayout) findViewById(R.id.ll_mainLayout);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.popup, ll_mainLayout, false);
        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(UserDetailActivity.this);

        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setContentView(customView);

        int a = (plus.getMeasuredWidth());
        int b = (array[1] + (30 + (plus.getMeasuredHeight())));

        //  int a = (array[0] + (plus.getMeasuredWidth()));
        //  int b = (array[1] - (1 * (plus.getMeasuredHeight())));

        mPopupWindow.showAtLocation(customView, Gravity.NO_GRAVITY, a, b);

        Button gallery = (Button) customView.findViewById(R.id.btn_gallery);
        Button camera = (Button) customView.findViewById(R.id.btn_camera);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPopupWindow.dismiss();
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


    }
}