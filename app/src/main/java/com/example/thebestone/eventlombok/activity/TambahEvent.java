package com.example.thebestone.eventlombok.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.thebestone.eventlombok.PreferencesEvent;
import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.adapter.ListViewKabAdapter;
import com.example.thebestone.eventlombok.firebase.Firebase;
import com.example.thebestone.eventlombok.helper.PublicVar;
import com.example.thebestone.eventlombok.models.User;
import com.example.thebestone.eventlombok.models.UserEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class TambahEvent extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{

    private static int PICK_IMAGE = 11;

    EditText editNama, editDesk, editTgl, editWaktu, editLokasi;
    Button btnPostEvent;
    ImageView imgEvent;
    Spinner spinJenisEvent;

    Uri uri;

    DatabaseReference dbRefEvent;
    StorageReference storageRef;
    PreferencesEvent myPref;
    ProgressDialog pd;

    String[] arrayKab = {"Bima", "Dompu", "Lombok Barat", "Lombok Tengah", "Lombok Timur", "Lombok Utara",
            "Sumbawa", "Sumbawa Bara", "Kota Bima", "Kota Mataram"};
    String[] arrayJenis ={"Workshop", "Seminar", "Festival", "Kompetisi", "Job Fair"};
    String photoEvent, kodeEvent, kodeImage;

    ArrayAdapter<String> adapterJenisEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_event);

        init();

        if (getIntent().getBooleanExtra("edit", false)) {
            isEditEvent();
        }

        editTgl.setOnFocusChangeListener(this);
        editTgl.setOnClickListener(this);
        editWaktu.setOnFocusChangeListener(this);
        editWaktu.setOnClickListener(this);
        editLokasi.setOnFocusChangeListener(this);
        editLokasi.setOnClickListener(this);

        btnPostEvent.setOnClickListener(this);

    }

    private void init() {
        pd = new ProgressDialog(this);
        pd.setTitle("Tunggu Sesaat");
        pd.setMessage("Event anda akan segera di post...");
        pd.setCancelable(false);
        pd.setButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(TambahEvent.this, "Batal", Toast.LENGTH_SHORT).show();
                    }
                });

                dbRefEvent = FirebaseDatabase.getInstance().getReference("events");

        myPref = new PreferencesEvent(this);

        btnPostEvent = findViewById(R.id.btnPostEvent);
        editNama = findViewById(R.id.editNamaEventTambah);
        editDesk = findViewById(R.id.editDeskripsiEventTambah);
        editTgl = findViewById(R.id.editTglTambah);
        editWaktu = findViewById(R.id.editWaktuTambah);
        editLokasi = findViewById(R.id.editLokasiTambah);
        imgEvent = findViewById(R.id.imgEventTambah);
        spinJenisEvent = findViewById(R.id.spinJenisEventTambah);

        adapterJenisEvent = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, arrayJenis);
        spinJenisEvent.setAdapter(adapterJenisEvent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPostEvent:

                final String nama, desc, tgl, waktu, lokasi, jenisEvent;

                nama = editNama.getText().toString();
                desc = editDesk.getText().toString();
                waktu = editWaktu.getText().toString();
                tgl = editTgl.getText().toString();
                lokasi = editLokasi.getText().toString();
                jenisEvent = spinJenisEvent.getSelectedItem().toString();

                kodeEvent = dbRefEvent.push().getKey();

                if (editEmpty()) {
                    Toast.makeText(this, "Semua kolom harus di isi", Toast.LENGTH_SHORT).show();
                } else {
                    if (btnPostEvent.getText().equals("Post Event")) {
                        addEvent(nama, desc, jenisEvent, lokasi, waktu, tgl);
                    } else {
                        updateEvent(PublicVar.userEventPublic.getKodeEvent(), nama, desc, jenisEvent, waktu, tgl, lokasi);
                    }
                }

                break;
            case R.id.editTglTambah:
                setTanggal();
                break;
            case R.id.editWaktuTambah:
                setWaktu();
                break;
            case R.id.editLokasiTambah:
                setKabupaten();
                break;
        }
    }

    public boolean editEmpty() {
        if (TextUtils.isEmpty(editNama.getText().toString()) || TextUtils.isEmpty(editDesk.getText().toString()) ||
                TextUtils.isEmpty(editWaktu.getText().toString()) || TextUtils.isEmpty(editTgl.getText().toString()) ||
                TextUtils.isEmpty(editLokasi.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    private void setTanggal() {
        final DatePicker datePicker = new DatePicker(this);
        AlertDialog.Builder setWaktu = new AlertDialog.Builder(this);
        setWaktu
                .setView(datePicker)
                .setPositiveButton("Atur", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int tgl, bulan, tahun;
                        tgl = datePicker.getDayOfMonth();
                        bulan = datePicker.getMonth() + 1;
                        tahun = datePicker.getYear();

                        editTgl.setText(tgl + "/" + bulan + "/" + tahun);
                    }
                })
                .create().show();
    }

    public void setWaktu() {
        final TimePicker timePicker = new TimePicker(this);
        AlertDialog.Builder setWaktu = new AlertDialog.Builder(this);
        setWaktu
                .setView(timePicker)
                .setPositiveButton("Atur", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editWaktu.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                    }
                })
                .create().show();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.editTglTambah:
                if (b) setTanggal();
                break;
            case R.id.editWaktuTambah:
                if (b) setWaktu();
                break;
            case R.id.editLokasiTambah:
                setKabupaten();
                break;
        }
    }

    public void selectPicture(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            kodeImage = UUID.randomUUID().toString();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imgEvent.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setKabupaten() {
        ListViewKabAdapter adapter = new ListViewKabAdapter(this, arrayKab);
        ListView listView = new ListView(this);
        listView.setAdapter(adapter);

        AlertDialog.Builder aletKab = new AlertDialog.Builder(this);
        aletKab.setView(listView);

        final Dialog dialog = aletKab.create();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editLokasi.setText(arrayKab[i]);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void addEvent(final String nama, final String desc, final String jenisEvent, final String lokasi, final String waktu, final String tgl) {

        storageRef = FirebaseStorage.getInstance().getReference("eventLombok/" + kodeImage);
        photoEvent = "https://firebasestorage.googleapis.com/v0/b/fir-q-7dedc.appspot.com/o/eventLombok%2F" + kodeImage + "?alt=media&token=30792162-a5fb-449a-93f8-5792c668fd60";

        if (uri == null) {
            Toast.makeText(this, "Silahkan Pilih Foto", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    UserEvent userEvent = new UserEvent(kodeEvent, myPref.getUserEmail(), myPref.getUserPhoto(), nama, desc, photoEvent, jenisEvent, lokasi, waktu, tgl);
                    dbRefEvent.child(kodeEvent).setValue(userEvent);

                    Toast.makeText(TambahEvent.this, "Event Telah Di Post", Toast.LENGTH_SHORT).show();
                    finish();
                    pd.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TambahEvent.this, "Terjadi Kesalahan Saat mem-Posting", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
    }

    public void updateEvent(final String kodeEvent, final String nama, final String desc, final String jenisEvent, final String waktu, final String tgl, final String lokasi) {
        pd.show();
        dbRefEvent = FirebaseDatabase.getInstance().getReference("events").child(kodeEvent);

        if (uri == null) {
            final UserEvent userEvent = new UserEvent(kodeEvent, myPref.getUserEmail(), myPref.getUserPhoto(), nama, desc, kodeImage, jenisEvent, lokasi, waktu, tgl);
            dbRefEvent.setValue(userEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    pd.dismiss();
                    finish();
                }
            });
        } else {
            storageRef = FirebaseStorage.getInstance().getReference("eventLombok/" + kodeImage);
            photoEvent = "https://firebasestorage.googleapis.com/v0/b/fir-q-7dedc.appspot.com/o/eventLombok%2F" + kodeImage + "?alt=media&token=30792162-a5fb-449a-93f8-5792c668fd60";
            final UserEvent userEvent = new UserEvent(kodeEvent, myPref.getUserEmail(), myPref.getUserPhoto(), nama, desc, photoEvent, jenisEvent, lokasi, waktu, tgl);
            storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dbRefEvent.setValue(userEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pd.dismiss();
                            finish();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TambahEvent.this, "Terjadi Kesalahan Saat mem-Posting", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
    }

    public void isEditEvent() {
        UserEvent userEvent = PublicVar.userEventPublic;

        editNama.setText(userEvent.getNamaEvent());
        editDesk.setText(userEvent.getDescEvent());
        editTgl.setText(userEvent.getTglEvent());
        editWaktu.setText(userEvent.getWaktuEvent());
        editLokasi.setText(userEvent.getLokasiEvent());
        btnPostEvent.setText("Perbaharui Event");
        kodeImage = PublicVar.userEventPublic.getPhotoEvent();

        Picasso.get().load(userEvent.getPhotoEvent()).into(imgEvent);

        spinJenisEvent.setSelection(adapterJenisEvent.getPosition(userEvent.getJenisEvent()));
    }
}
