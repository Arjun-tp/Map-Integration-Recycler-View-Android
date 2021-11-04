package com.example.test3mapandlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test3mapandlist.EntityClass.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;

public class UpdateActivity extends AppCompatActivity {

    private int PICK_IMAGE = 1, CAMERA_REQUEST = 200;
    Uri imageUri;
    Spinner genderSp;
    EditText nameHere, latHere, lngHere;
    Button countrySelect, save, back, update, upload;
    CalendarView calender;
    Bitmap imageSelected;
    ImageView imageView;
    TextView countryText;
    String calenderDateSelected = "";
    String []gender = {"Male", "Female", "Others"};
    public static String countrySelected = "";
    String genderSelected = "";
    String name_txt = "", gender_txt = "", dob_txt = "", country_txt = "", lat_txt = "", lng_txt = "";
    String personName, personGender, personDob = "", personCountry = "", personLat, personLng, personId;
    byte[] personImage = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        genderSp = findViewById(R.id.genderSpinner);
        nameHere = findViewById(R.id.extName);
        calender = findViewById(R.id.calView);
        latHere = findViewById(R.id.extLatitude);
        lngHere = findViewById(R.id.extLongitude);
        countrySelect = findViewById(R.id.countryBtn);
        countryText = findViewById(R.id.txvCountry);
        imageView = findViewById(R.id.personImg);
        save = findViewById(R.id.btnSave);
        back = findViewById(R.id.btnBack);
        update = findViewById(R.id.btnUpdate);
        upload = findViewById(R.id.btnUpload);

        update.setVisibility(View.INVISIBLE);
        save.setVisibility(View.VISIBLE);

        personName = getIntent().getStringExtra("nameSent");
        personGender = getIntent().getStringExtra("genderSent");
        personDob = getIntent().getStringExtra("birthdaySent");
        personCountry = getIntent().getStringExtra("countrySent");
        personLat = getIntent().getStringExtra("latSent");
        personLng = getIntent().getStringExtra("lngSent");
        personId = getIntent().getStringExtra("idSent");
        personImage = getIntent().getByteArrayExtra("imageSent");


        //Upload Image
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openGallery(view);
            }
        });

        nameHere.setText(personName);
        latHere.setText(personLat);
        lngHere.setText(personLng);

        if(personImage != null){
            Bitmap selectedImageHere = BitmapFactory.decodeByteArray(personImage,0,personImage.length);
            imageView.setImageBitmap(selectedImageHere);
        } else {
        }

        name_txt = nameHere.getText().toString().trim();

        // Save and Update button Visibilities
        if (!name_txt.equals("")){
            save.setVisibility(View.INVISIBLE);
            update.setVisibility(View.VISIBLE);

        }

        if (countrySelected != ""){
            countryText.setText(countrySelected);
        } else {
            countryText.setText(personCountry);
        }
        genderSelected = "Male";


        ArrayAdapter aa = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, gender);
        genderSp.setAdapter(aa);

        // Gender Function
        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderSelected = gender[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // Country Selection Function
        countrySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, CountryList.class);
                intent.putExtra("nameSent", personName);
                intent.putExtra("birthdaySent", personDob);
                intent.putExtra("countrySent", personCountry);
                intent.putExtra("genderSent", personGender);
                intent.putExtra("latSent", personLat);
                intent.putExtra("lngSent",personLng);
                intent.putExtra("idSent", personId);
                startActivity(intent);
            }
        });

        // Calender Function
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                i1 = i1+1;
                String date = i + "/" + i1 + "/" + i2;
                Log.d("CalenderActivity", "On selectedDay Change date: " + date);
                calenderDateSelected = date;
            }
        });

        // Back Button Function
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(in);
            }
        });

        // Save to DB Function
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageSelected.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();

                UserModel model = new UserModel();
                model.setName(nameHere.getText().toString());
                model.setLat(latHere.getText().toString());
                model.setLng(lngHere.getText().toString());
                model.setBirthday(calenderDateSelected);
                model.setGender(genderSelected);
                model.setCountry(countrySelected);
                model.setUserImage(bytes);

                DatabaseClass.getDatabase(getApplicationContext()).getDao().insertAllData(model);
                nameHere.setText("");
                latHere.setText("");
                lngHere.setText("");
                countrySelected = "";
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                DatabaseClass.getDatabase(getApplicationContext()).getDao().getAllData();
                Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // Update DB Function
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageSelected.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();

                String id = getIntent().getStringExtra("idSent");

                name_txt = nameHere.getText().toString().trim();
                gender_txt = genderSelected;
                dob_txt = calenderDateSelected;
                country_txt = countrySelected;
                lat_txt = latHere.getText().toString().trim();
                lng_txt = lngHere.getText().toString().trim();

                DatabaseClass.getDatabase(getApplicationContext()).getDao().updateData(name_txt, country_txt, dob_txt,
                        gender_txt, bytes, lat_txt, lng_txt, Integer.parseInt(id));
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                DatabaseClass.getDatabase(getApplicationContext()).getDao().getAllData();
            }
        });

    }

    // Open the gallery to choose image
    private void openGallery(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Title"), CAMERA_REQUEST);
    }

    // Image Selection and viewing
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                imageSelected = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                imageView.setImageBitmap(imageSelected);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}