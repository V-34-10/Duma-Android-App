package com.mockable.dumav10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mockable.dumav10.databinding.ActivityHistoryBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener/*, Animation.AnimationListener*/ {

    private TextView phraseDialog;
    private ImageView background_person, btn_back;
    private RelativeLayout background_scene;
    private LinearLayout background_dialog;
    //private ConstraintLayout background_persons;

    private DatabaseReference mDateBase;
    private StorageReference mStorageBase;

    private ArrayList<String> listData;
    private Integer id = 0;
    private final String BASE_KEY = "guardian_legends";

    private Uri uploadUri;
    int select = 1;

    private Animation inAnimation;
    private Animation outAnimation;

    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*Picasso picasso =  new Picasso.Builder(this).downloader(new OkHttp3Downloader(getCacheDir(), 250000000)).build();
        Picasso.setSingletonInstance(picasso);*/
        init();

       /* mDateBase.child(Objects.requireNonNull(mDateBase.getKey())).get()
                .addOnSuccessListener { data ->
                println("Пользователь: ${data.value}")
        }
    .addOnFailureListener { exception ->
                println("Не удалось получить пользователя из базы")
            exception.printStackTrace()
        }*/


        //mDateBase = FirebaseDatabase.getInstance().getReference("guardian_legends");

        /*
        setDataFromDB("Батько схопив мене за зап'ястя і стиснув.", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fnotperson.png?alt=media&token=219f18ba-cb60-4d5b-97d0-d3d2ac88571b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_about_history.png?alt=media&token=d7ec6767-bd20-41bd-97e4-7532c9367ffe", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fstairs.png?alt=media&token=3790c555-cc30-4500-8b65-30d0bd41060a");
        setDataFromDB("Татко, відпусти! Я все одно піду!", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fzolotovlaska3.png?alt=media&token=11f0c0a3-34eb-44c8-9d20-77a49dbbba88", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_zolotovlaska.png?alt=media&token=a88e58be-f73e-4eaf-8a06-3e9029ad5a7b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fstairs.png?alt=media&token=3790c555-cc30-4500-8b65-30d0bd41060a");
        setDataFromDB("Що ти наробила! Донька! Ганьба!", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fking3.png?alt=media&token=0dcffb88-dd5d-4684-b08e-f910db72987c", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_king.png?alt=media&token=febd4fac-7944-469f-bc5f-50b71f91c835", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fstairs.png?alt=media&token=3790c555-cc30-4500-8b65-30d0bd41060a");
        setDataFromDB("«Схлипуючи відповіла:»", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fnotperson.png?alt=media&token=219f18ba-cb60-4d5b-97d0-d3d2ac88571b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_about_history.png?alt=media&token=d7ec6767-bd20-41bd-97e4-7532c9367ffe", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fstairs.png?alt=media&token=3790c555-cc30-4500-8b65-30d0bd41060a");
        setDataFromDB("Татко, я помру без нього!", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fzolotovlaska3.png?alt=media&token=11f0c0a3-34eb-44c8-9d20-77a49dbbba88", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_zolotovlaska.png?alt=media&token=a88e58be-f73e-4eaf-8a06-3e9029ad5a7b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fstairs.png?alt=media&token=3790c555-cc30-4500-8b65-30d0bd41060a");
        setDataFromDB("Гірський король так розлютився, що на небі ясному вибухнув грім, почорніли хмари, а вітер збивав з ніг.", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fnotperson.png?alt=media&token=219f18ba-cb60-4d5b-97d0-d3d2ac88571b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_about_history.png?alt=media&token=d7ec6767-bd20-41bd-97e4-7532c9367ffe", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fstairs.png?alt=media&token=3790c555-cc30-4500-8b65-30d0bd41060a");
        setDataFromDB("Король заговорив голосним, хрипким басом, від якого забігали мурашки по шкірі.", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fnotperson.png?alt=media&token=219f18ba-cb60-4d5b-97d0-d3d2ac88571b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_about_history.png?alt=media&token=d7ec6767-bd20-41bd-97e4-7532c9367ffe", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fstairs.png?alt=media&token=3790c555-cc30-4500-8b65-30d0bd41060a");
        setDataFromDB("Я ГІРСЬКИЙ КОРОЛЬ! НАКАЗУЮ ЗАМКУ СТАТИ ГОРОЮ НА ВІКИ ВІКІВ!", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fking4.png?alt=media&token=4a6bcb6f-9a1e-4727-ace4-7e84f2d3899a", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_king.png?alt=media&token=febd4fac-7944-469f-bc5f-50b71f91c835", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fstairs.png?alt=media&token=3790c555-cc30-4500-8b65-30d0bd41060a");
        setDataFromDB("ПАПЕНЬКА-А-А! НІ!", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fzolotovlaska3.png?alt=media&token=11f0c0a3-34eb-44c8-9d20-77a49dbbba88", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_zolotovlaska.png?alt=media&token=a88e58be-f73e-4eaf-8a06-3e9029ad5a7b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fstairs.png?alt=media&token=3790c555-cc30-4500-8b65-30d0bd41060a");
        setDataFromDB("Тільки він це сказав і потемніли скляні палаци і перетворилися на гору. Магія замурувала навіки і короля, і його дочку.", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fnotperson.png?alt=media&token=219f18ba-cb60-4d5b-97d0-d3d2ac88571b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_about_history.png?alt=media&token=d7ec6767-bd20-41bd-97e4-7532c9367ffe", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fwalled_castle.png?alt=media&token=3d6fa7e2-bb85-4d20-aa58-ef2293ee9a30");
        setDataFromDB("НІ-І-І!", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fnotperson.png?alt=media&token=219f18ba-cb60-4d5b-97d0-d3d2ac88571b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_zolotovlaska.png?alt=media&token=a88e58be-f73e-4eaf-8a06-3e9029ad5a7b", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fwalled_castle.png?alt=media&token=3d6fa7e2-bb85-4d20-aa58-ef2293ee9a30");
        setDataFromDB("А-А-А! НІ!", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fperson%2Fmagdalena.png?alt=media&token=045e1dc9-f7fa-4e96-98e1-5f4a10f62e80", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fdialog%2Fdialog_magdalena.png?alt=media&token=92f4b519-af75-4312-a7ca-91f1dd3111b2", "https://firebasestorage.googleapis.com/v0/b/duma-4ab28.appspot.com/o/Part1%2Fbackground%2Fwalled_castle.png?alt=media&token=3d6fa7e2-bb85-4d20-aa58-ef2293ee9a30");
        */
        //getDataFromDB(1);

        //phraseDialog.setText(listData.get(0));
        //Toast.makeText(this, listData.get(0), Toast.LENGTH_SHORT).show();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void init() {


        phraseDialog = binding.textDialog;
        background_scene = binding.background;
        background_person = binding.persons;
        background_dialog = binding.aboutHistory;
        btn_back = binding.btnBack;

        //outAnimation.setAnimationListener(this);
        btn_back.setOnClickListener(this);
        background_dialog.setOnClickListener(this);
        mDateBase = FirebaseDatabase.getInstance().getReference(BASE_KEY);
    }

    private void getDataFromDB(int id) {
        inAnimation = new AlphaAnimation(0, 1);
        inAnimation.setDuration(2000);
        outAnimation = new AlphaAnimation(1, 0);
        outAnimation.setDuration(2000);
        outAnimation.setBackgroundColor(Color.BLACK);
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /*if(listData.size() > 0){
                    listData.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
                    History phrase = ds.getValue(History.class);
                    assert phrase != null;
                    listData.add(phrase.text);
                }*/

                for (DataSnapshot ds : snapshot.getChildren()) {
                    History text = ds.getValue(History.class);
                    assert text != null;
                    if (text.getId() == id) {

                        /*background_person.setImageResource(getResources().getIdentifier(text.person, "drawable", getPackageName()));
                        background_scene.setBackgroundResource(getResources().getIdentifier(text.background, "drawable", getPackageName()));
                        background_dialog.setBackgroundResource(getResources().getIdentifier(text.dialog, "drawable", getPackageName()));
                        */

                        /*Picasso.get().load(text.background).resize(0, 800).onlyScaleDown().into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                background_scene.setBackground(new BitmapDrawable(bitmap));
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                            }
                        });*/

                        //background_scene.setAnimation(outAnimation);
                        Glide.with(HistoryActivity.this).load(text.getBackground()).into(new CustomTarget<Drawable>() {

                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                //background_scene.setBackground(resource);


                                //background_scene.setBackground(new ColorDrawable(Color.BLACK));
                                //background_scene.setAnimation(inAnimation);
                                background_scene.setBackground(resource);

                                //background_scene.startAnimation(Animation);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });


                        background_person.setAnimation(outAnimation);
                        Glide.with(HistoryActivity.this).load(text.getPerson()).into(background_person);

                        /*Glide.with(HistoryActivity.this).load(text.getPerson()).into(new CustomTarget<Drawable>() {

                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                background_person.setBackground(resource);
                                //background_scene.setAnimation(inAnimation);
                                //background_scene.setAnimation(outAnimation);
                                //background_scene.startAnimation(Animation);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });*/

                        background_person.setAnimation(inAnimation);

                        background_dialog.setAnimation(outAnimation);
                        Glide.with(HistoryActivity.this).load(text.getDialog()).into(new CustomTarget<Drawable>() {

                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                background_dialog.setAnimation(inAnimation);
                                background_dialog.setBackground(resource);

                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });



                        //Picasso.get().load(text.person).fit().centerInside().into(background_person);
                        /*Picasso.get().load(text.dialog).resize(0, 200).onlyScaleDown().into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                background_dialog.setBackground(new BitmapDrawable(bitmap));
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                            }
                        });*/

                        background_dialog.setAnimation(outAnimation);

                        phraseDialog.setText(text.getText());
                        background_dialog.setAnimation(inAnimation);
                        if (text.getChoice()){

                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", error.getMessage());
            }
        };
        mDateBase.addValueEventListener(vListener);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.textZolotovlaska, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        //LinearLayout toastContainer = (LinearLayout) toast.getView();
        // Устанавливаем прозрачность у контейнера
        //toastContainer.setBackgroundResource(R.drawable.dialog_toast_history);
        toast.show();
    }

    private void setDataFromDB(String Text, String Person, String Dialog, String Background, Boolean choice) {

        //String id = mDateBase.push().getKey();
        id++;
        History history = new History(id, Text, Person, Dialog, Background, choice);
        mDateBase.push().setValue(history);

    }

    private void uploadImageStorage(ImageView imageView) {
        Bitmap bitMap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageBase.child(System.currentTimeMillis() + "image");
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult();
            }
        });

    }

    @Override
    public void onClick(View view) {
        Animation Animation = AnimationUtils.loadAnimation(HistoryActivity.this, R.anim.alpha);
        Intent nextActivity;
        switch (view.getId()) {
            case R.id.btn_back:
                btn_back.startAnimation(Animation);
                nextActivity = new Intent(HistoryActivity.this, MenuActivity.class);
                startActivity(nextActivity);
                finish();
                break;
            case R.id.about_history:
                select++;
                /*if(select == 12){
                    showToast(view);
                }*/


                getDataFromDB(select);
                break;
        }
    }

    /*@Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }*/
}