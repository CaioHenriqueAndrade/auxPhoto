package caiohenrique.auxiliarphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import caiohenrique.auxphoto.AuxiliarPhoto;

public class MainActivity extends AppCompatActivity implements AuxiliarPhoto.OnDownloadedListener, View.OnClickListener {

    ImageView imageview;
    AuxiliarPhoto auxiliarPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageview = (ImageView) findViewById(R.id.imageview);

        auxiliarPhoto = new AuxiliarPhoto(this);
        Bitmap bt = auxiliarPhoto.loadImageFromStorageOrDownload("dir", "image", "https://avatars1.githubusercontent.com/u/6407041?v=4", this);

        if (bt == null) {
            //if not exists photo in phone
            //will download
        } else {
            // exits photo in phone

            //300 is scale of size of bitmap...
            //more size, more memory
            bt = auxiliarPhoto.modifyScaleOfFile(auxiliarPhoto.getFile("directory","imageName"),300);
            //your can do in background this action in other Thread if your preference


            imageview.setImageBitmap(bt);

            bt = null;
            // memory of this object is reduced now
        }


        imageview.setOnClickListener(this);
    }

    @Override
    public void onDownloadCompleted(Bitmap bitmap, String nameImage) {
        if (bitmap != null) {
            // 0 is quality, if 0 is desconsidered (max is 100) 1 - 100
            auxiliarPhoto.saveToInternalStorage("directory", bitmap, "imageName", 0);
            imageview.setImageBitmap(bitmap);
            bitmap = null;
        } else {
            Toast.makeText(getApplicationContext(), "error in download, not connected?", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        Intent it = new Intent(this,ActivityMostImages.class);
        startActivity(it);
    }
}
