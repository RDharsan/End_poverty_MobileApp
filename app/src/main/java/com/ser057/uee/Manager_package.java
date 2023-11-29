package com.example.mad;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

//Package details functions
public class Manager_package extends AppCompatActivity {
    DBpackage db;
    EditText fId,fName,fDate,fLocation,fTime;
    Button add,delete,update,view,logout,search;
    AwesomeValidation awesomeValidation;
    String[] type_of_package = {"Per day package ", "Monthly package", "Pay as you go"};
    private TextView checktext;
    private Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpackage);
//        checkButton = findViewById(R.id.cb);
//        checktext = findViewById(R.id.ct);

        db=new DBpackage(this);

        fId=(EditText)findViewById(R.id.p1);
        fName=(EditText)findViewById(R.id.p2);
        fDate=(EditText)findViewById(R.id.p3);
        fLocation=(EditText)findViewById(R.id.p4);
        fTime=(EditText)findViewById(R.id.ct);


        add=findViewById(R.id.add);
        delete=findViewById(R.id.delete);
        update=findViewById(R.id.update);
        view=findViewById(R.id.view);
        search=findViewById(R.id.search);
        logout=findViewById(R.id.log);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
        SearchData();
        awesomeValidation = new AwesomeValidation( ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.p1, RegexTemplate.NOT_EMPTY, R.string.invalid_eventID);
        awesomeValidation.addValidation(this, R.id.p2, RegexTemplate.NOT_EMPTY, R.string.invalid_eventname);
        awesomeValidation.addValidation(this,R.id.p3, Range.closed(500,20000),R.string.invalid_eventdate);
        awesomeValidation.addValidation(this, R.id.p4, RegexTemplate.NOT_EMPTY, R.string.invalid_eventloc );
        awesomeValidation.addValidation(this, R.id.ct, RegexTemplate.NOT_EMPTY, R.string.invalid_eventtime );

//        checkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (pCost.getText().toString().length() == 0) {
//                    pCost.setText("0");
//                }
////                checktext.setText(String.valueOf(calculate()));
//
//
//            }
//        });


        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log=new Intent( Manager_package.this, Manager.class );

                startActivity( log );
            }
        } );



        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(onItemSelectedListener1);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type_of_package);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner1.setAdapter(aa);



    }

    final AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String s1 = String.valueOf(type_of_package[position]);
                    fName.setText(s1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };




    public void DeleteData(){
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows =db.deleteData(fId.getText().toString());
                        if(deletedRows >0) {
                            Toast.makeText( Manager_package.this, "Data deleted", Toast.LENGTH_LONG ).show();
                            clearControls();
                        } else
                            Toast.makeText(Manager_package.this,"Data Not deleted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void UpdateData(){
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate= db.updateData(fId.getText().toString(),fName.getText().toString(),fDate.getText().toString(),fLocation.getText().toString(),fTime.getText().toString());
                        if(isUpdate==true && awesomeValidation.validate()) {
                            Toast.makeText( Manager_package.this, "Data updated", Toast.LENGTH_LONG ).show();
                            clearControls();
                        } else
                            Toast.makeText(Manager_package.this,"Data Not updated",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void AddData(){
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted=db.insertData(fId.getText().toString(),fName.getText().toString(),fDate.getText().toString(),fLocation.getText().toString(),fTime.getText().toString());
                        if(isInserted == true && awesomeValidation.validate()) {
                            Toast.makeText( Manager_package.this, "Data Inserted", Toast.LENGTH_SHORT ).show();
                            clearControls();
                        } else
                            Toast.makeText(Manager_package.this,"Data  notInserted",Toast.LENGTH_SHORT).show();


                    }
                }
        );
    }  public void viewAll(){
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.getAllData();
                        Cursor res =db.getAllData();
                        if(res.getCount()==0){
                            showMessage("View is Empty !!!","No Data Found");
                            return;
                        }
                        StringBuffer buffer =new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Event ID :"+res.getString(0)+"\n");
                            buffer.append("Event Name :"+res.getString(1)+"\n");
                            buffer.append("Event Date :"+res.getString(2)+"\n");
                            buffer.append("Event Location:"+res.getString(3)+"\n");
                            buffer.append("Event Time:"+res.getString(4)+"\n");

                        }
                        showMessage("Food Event Details",buffer.toString());

                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void SearchData(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = db.searchData(fId.getText().toString());
                if (data.getCount() == 0) {
                    //Show Message
                    showMessage("Error ", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (data.moveToNext()) {
                    fId.setText( data.getString(0));
                    fName.setText( data.getString(1));
                   fDate.setText( data.getString(2));
                    fLocation.setText( data.getString(3));
                    fTime.setText( data.getString(4));


                }
            }
        });

    }
    private void clearControls(){
        fId.setText("");
        fName.setText("");
        fDate.setText("");
        fLocation.setText("");
        fTime.setText("");

    }

//    public float calculate() {
//        float rcost1 = 0.25F;
//        float rcost2 =  12000;
//
//        float rnum = Integer.parseInt(pCost.getText().toString());
//
//
//        if(pCost.getText().toString().equalsIgnoreCase("10000")){
//
//           float cal = rcost1 * rnum;
//            return cal;
//
//
//        }
//        else{
//            float cal = rcost2 * rnum;
//            return cal;
//        }
//    }


}



