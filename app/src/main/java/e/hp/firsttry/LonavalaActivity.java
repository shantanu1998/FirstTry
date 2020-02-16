package e.hp.firsttry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class LonavalaActivity extends AppCompatActivity {
    Button Choose,Upload;
    ImageView img;
    private StorageReference mStorageRef;
    private StorageTask uploadTask;

    public Uri imguri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lonavala);
        Choose =(Button)findViewById(R.id.Choosebutton);
        Upload =(Button)findViewById(R.id.UploadButton);
        img = (ImageView)findViewById(R.id.imageView2);
        mStorageRef= FirebaseStorage.getInstance().getReference().child("images/");
        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileChooser();
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
     //           FileUploader();

                if(uploadTask!=null && uploadTask.isInProgress()) {
                    Toast.makeText(LonavalaActivity.this,"Upload in progress",Toast.LENGTH_SHORT).show();
                }else {
                    FileUploader();

                }
            }
        });



    }
    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private  void FileUploader() {
                //    StorageReference Ref=mStorageRef.child(getExtension(imguri));

        if(imguri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
           try {
               Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
               byte[] data = baos.toByteArray();
               //UploadTask uploadTask2 = mStorageRef.putBytes(data);

               StorageReference ref = mStorageRef.child("HEllO/*" + UUID.randomUUID().toString());
               ref.putBytes(data)
                       .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               progressDialog.dismiss();
                               Toast.makeText(LonavalaActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               progressDialog.dismiss();
                               Toast.makeText(LonavalaActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       })
                       .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                               double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                       .getTotalByteCount());
                               progressDialog.setMessage("Uploaded " + (int) progress + "%");
                           }
                       });
           }catch(IOException ioException)
           {
               ioException.printStackTrace();
           }
        }

     }


/*
Toast.makeText(LonavalaActivity.this,"Upload 1",Toast.LENGTH_SHORT).show();

        uploadTask=Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                       // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(LonavalaActivity.this,"Upload Sucess",Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }*/



    public void FileChooser(){
        Intent intent=new Intent();
        intent.setType("images/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1  && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imguri = data.getData();
            img.setImageURI(imguri);

        }
    }
}
