package com.example.food.Profile;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.food.R;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private static final  int NUM_GRID_COLUMNS = 3;
    private Context mContext = ProfileActivity.this;

    private ProgressBar mProgressBar;
    private ImageView profilePhoto;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");

        init();
//        setupBottomNavigationView();
//        setupToolbar();
//        setupActivityWidgets();
//        setProfileImage();
//
//        tempGridSetup();
    }

    private void init(){
        Log.d(TAG, "init: inflating" + getString(R.string.profile_fragment));

//
//        ProfileFragment fragment = new ProfileFragment();
//        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, fragment);
//        transaction.commit();
    }

//    private void tempGridSetup(){
//        ArrayList<String> imgURLs = new ArrayList<>();
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/128172432_4799697463437188_2921693663005201794_o.jpg?_nc_cat=100&ccb=2&_nc_sid=8bfeb9&_nc_ohc=9pw7G-qpJiUAX8fFrlu&_nc_ht=scontent.fomr1-1.fna&oh=7f307ec2cb65e59145ad517b636721cf&oe=5FFC29A8");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/117534516_4274989859241287_2854300130299233362_o.jpg?_nc_cat=111&ccb=2&_nc_sid=8bfeb9&_nc_ohc=N25w8mfnEbsAX_7W2WW&_nc_ht=scontent.fomr1-1.fna&oh=51c29bce63f8a2fb49c00e8497770752&oe=5FFDED70");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/93956747_3818689994871278_5059883860267892736_o.jpg?_nc_cat=102&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0pQIMHX-ViMAX9qK_3f&_nc_ht=scontent.fomr1-1.fna&oh=d0a2f2b38d13298dd600290c6dc55315&oe=5FFA651F");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/90816464_3728525350554410_3972813515797823488_o.jpg?_nc_cat=108&ccb=2&_nc_sid=174925&_nc_ohc=HQcLtc4-O-EAX-vDnEg&_nc_ht=scontent.fomr1-1.fna&oh=545c5f2dd41dc8b63eb98bc070ded2fc&oe=5FFB2F36");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/81440250_3457385634335051_1057416614653198336_o.jpg?_nc_cat=101&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0LCWjGovRLgAX_PY3IC&_nc_ht=scontent.fomr1-1.fna&oh=0c441fa90d7d4238c1fb84c9e7668569&oe=5FFDF412");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/55674628_2686723838067905_5723336911704031232_o.jpg?_nc_cat=104&ccb=2&_nc_sid=174925&_nc_ohc=-2Oi_HMWj8AAX8uf8Ne&_nc_ht=scontent.fomr1-1.fna&oh=2e527b67cd8e1081c05a7e5439687f01&oe=5FFC21B9");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/128172432_4799697463437188_2921693663005201794_o.jpg?_nc_cat=100&ccb=2&_nc_sid=8bfeb9&_nc_ohc=9pw7G-qpJiUAX8fFrlu&_nc_ht=scontent.fomr1-1.fna&oh=7f307ec2cb65e59145ad517b636721cf&oe=5FFC29A8");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/117534516_4274989859241287_2854300130299233362_o.jpg?_nc_cat=111&ccb=2&_nc_sid=8bfeb9&_nc_ohc=N25w8mfnEbsAX_7W2WW&_nc_ht=scontent.fomr1-1.fna&oh=51c29bce63f8a2fb49c00e8497770752&oe=5FFDED70");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/93956747_3818689994871278_5059883860267892736_o.jpg?_nc_cat=102&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0pQIMHX-ViMAX9qK_3f&_nc_ht=scontent.fomr1-1.fna&oh=d0a2f2b38d13298dd600290c6dc55315&oe=5FFA651F");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/90816464_3728525350554410_3972813515797823488_o.jpg?_nc_cat=108&ccb=2&_nc_sid=174925&_nc_ohc=HQcLtc4-O-EAX-vDnEg&_nc_ht=scontent.fomr1-1.fna&oh=545c5f2dd41dc8b63eb98bc070ded2fc&oe=5FFB2F36");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/81440250_3457385634335051_1057416614653198336_o.jpg?_nc_cat=101&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0LCWjGovRLgAX_PY3IC&_nc_ht=scontent.fomr1-1.fna&oh=0c441fa90d7d4238c1fb84c9e7668569&oe=5FFDF412");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/128172432_4799697463437188_2921693663005201794_o.jpg?_nc_cat=100&ccb=2&_nc_sid=8bfeb9&_nc_ohc=9pw7G-qpJiUAX8fFrlu&_nc_ht=scontent.fomr1-1.fna&oh=7f307ec2cb65e59145ad517b636721cf&oe=5FFC29A8");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/117534516_4274989859241287_2854300130299233362_o.jpg?_nc_cat=111&ccb=2&_nc_sid=8bfeb9&_nc_ohc=N25w8mfnEbsAX_7W2WW&_nc_ht=scontent.fomr1-1.fna&oh=51c29bce63f8a2fb49c00e8497770752&oe=5FFDED70");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/93956747_3818689994871278_5059883860267892736_o.jpg?_nc_cat=102&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0pQIMHX-ViMAX9qK_3f&_nc_ht=scontent.fomr1-1.fna&oh=d0a2f2b38d13298dd600290c6dc55315&oe=5FFA651F");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/90816464_3728525350554410_3972813515797823488_o.jpg?_nc_cat=108&ccb=2&_nc_sid=174925&_nc_ohc=HQcLtc4-O-EAX-vDnEg&_nc_ht=scontent.fomr1-1.fna&oh=545c5f2dd41dc8b63eb98bc070ded2fc&oe=5FFB2F36");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/81440250_3457385634335051_1057416614653198336_o.jpg?_nc_cat=101&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0LCWjGovRLgAX_PY3IC&_nc_ht=scontent.fomr1-1.fna&oh=0c441fa90d7d4238c1fb84c9e7668569&oe=5FFDF412");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/128172432_4799697463437188_2921693663005201794_o.jpg?_nc_cat=100&ccb=2&_nc_sid=8bfeb9&_nc_ohc=9pw7G-qpJiUAX8fFrlu&_nc_ht=scontent.fomr1-1.fna&oh=7f307ec2cb65e59145ad517b636721cf&oe=5FFC29A8");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/117534516_4274989859241287_2854300130299233362_o.jpg?_nc_cat=111&ccb=2&_nc_sid=8bfeb9&_nc_ohc=N25w8mfnEbsAX_7W2WW&_nc_ht=scontent.fomr1-1.fna&oh=51c29bce63f8a2fb49c00e8497770752&oe=5FFDED70");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/93956747_3818689994871278_5059883860267892736_o.jpg?_nc_cat=102&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0pQIMHX-ViMAX9qK_3f&_nc_ht=scontent.fomr1-1.fna&oh=d0a2f2b38d13298dd600290c6dc55315&oe=5FFA651F");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/90816464_3728525350554410_3972813515797823488_o.jpg?_nc_cat=108&ccb=2&_nc_sid=174925&_nc_ohc=HQcLtc4-O-EAX-vDnEg&_nc_ht=scontent.fomr1-1.fna&oh=545c5f2dd41dc8b63eb98bc070ded2fc&oe=5FFB2F36");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/81440250_3457385634335051_1057416614653198336_o.jpg?_nc_cat=101&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0LCWjGovRLgAX_PY3IC&_nc_ht=scontent.fomr1-1.fna&oh=0c441fa90d7d4238c1fb84c9e7668569&oe=5FFDF412");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/55674628_2686723838067905_5723336911704031232_o.jpg?_nc_cat=104&ccb=2&_nc_sid=174925&_nc_ohc=-2Oi_HMWj8AAX8uf8Ne&_nc_ht=scontent.fomr1-1.fna&oh=2e527b67cd8e1081c05a7e5439687f01&oe=5FFC21B9");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/128172432_4799697463437188_2921693663005201794_o.jpg?_nc_cat=100&ccb=2&_nc_sid=8bfeb9&_nc_ohc=9pw7G-qpJiUAX8fFrlu&_nc_ht=scontent.fomr1-1.fna&oh=7f307ec2cb65e59145ad517b636721cf&oe=5FFC29A8");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/117534516_4274989859241287_2854300130299233362_o.jpg?_nc_cat=111&ccb=2&_nc_sid=8bfeb9&_nc_ohc=N25w8mfnEbsAX_7W2WW&_nc_ht=scontent.fomr1-1.fna&oh=51c29bce63f8a2fb49c00e8497770752&oe=5FFDED70");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/93956747_3818689994871278_5059883860267892736_o.jpg?_nc_cat=102&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0pQIMHX-ViMAX9qK_3f&_nc_ht=scontent.fomr1-1.fna&oh=d0a2f2b38d13298dd600290c6dc55315&oe=5FFA651F");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/90816464_3728525350554410_3972813515797823488_o.jpg?_nc_cat=108&ccb=2&_nc_sid=174925&_nc_ohc=HQcLtc4-O-EAX-vDnEg&_nc_ht=scontent.fomr1-1.fna&oh=545c5f2dd41dc8b63eb98bc070ded2fc&oe=5FFB2F36");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/81440250_3457385634335051_1057416614653198336_o.jpg?_nc_cat=101&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0LCWjGovRLgAX_PY3IC&_nc_ht=scontent.fomr1-1.fna&oh=0c441fa90d7d4238c1fb84c9e7668569&oe=5FFDF412");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/128172432_4799697463437188_2921693663005201794_o.jpg?_nc_cat=100&ccb=2&_nc_sid=8bfeb9&_nc_ohc=9pw7G-qpJiUAX8fFrlu&_nc_ht=scontent.fomr1-1.fna&oh=7f307ec2cb65e59145ad517b636721cf&oe=5FFC29A8");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/117534516_4274989859241287_2854300130299233362_o.jpg?_nc_cat=111&ccb=2&_nc_sid=8bfeb9&_nc_ohc=N25w8mfnEbsAX_7W2WW&_nc_ht=scontent.fomr1-1.fna&oh=51c29bce63f8a2fb49c00e8497770752&oe=5FFDED70");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/93956747_3818689994871278_5059883860267892736_o.jpg?_nc_cat=102&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0pQIMHX-ViMAX9qK_3f&_nc_ht=scontent.fomr1-1.fna&oh=d0a2f2b38d13298dd600290c6dc55315&oe=5FFA651F");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/90816464_3728525350554410_3972813515797823488_o.jpg?_nc_cat=108&ccb=2&_nc_sid=174925&_nc_ohc=HQcLtc4-O-EAX-vDnEg&_nc_ht=scontent.fomr1-1.fna&oh=545c5f2dd41dc8b63eb98bc070ded2fc&oe=5FFB2F36");
//        imgURLs.add("https://scontent.fomr1-1.fna.fbcdn.net/v/t1.0-9/81440250_3457385634335051_1057416614653198336_o.jpg?_nc_cat=101&ccb=2&_nc_sid=8bfeb9&_nc_ohc=0LCWjGovRLgAX_PY3IC&_nc_ht=scontent.fomr1-1.fna&oh=0c441fa90d7d4238c1fb84c9e7668569&oe=5FFDF412");
//
//        setupImageGrid(imgURLs);
//    }
//
//    private void setupImageGrid(ArrayList<String> imgURLs){
//        GridView gridView = findViewById(R.id.gridView);
//
//        int gridWidth = getResources().getDisplayMetrics().widthPixels;
//        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
//        gridView.setColumnWidth(imageWidth);
//
//
//        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs);
//        gridView.setAdapter(adapter);
//
//    }
//
//    private void setProfileImage() {
//        Log.d(TAG, "setProfileImage: setting profile photo");
//        String imgURL = "https://scontent.ftsr1-2.fna.fbcdn.net/v/t1.0-9/128932778_4816531301753804_7446855585350752572_o.jpg?_nc_cat=109&ccb=2&_nc_sid=09cbfe&_nc_ohc=6WWMGm0VvskAX9W1wEl&_nc_ht=scontent.ftsr1-2.fna&oh=2af36f4dd7e515682f836eba9d4d14bd&oe=5FFAE6FB";
//        UniversalImageLoader.setImage(imgURL, profilePhoto, mProgressBar, "");
//    }
//
//    private void setupActivityWidgets(){
//        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
//        mProgressBar.setVisibility(View.GONE);
//        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void setupToolbar()
//    {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
//        setSupportActionBar(toolbar);
//
//        ImageView profileMenu = (ImageView) findViewById(R.id.profileMenu);
//        profileMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to account settings");
//                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//    }
//
//
//    //BottomNavigationView setup-making this method to reuse it in aLL OUR ACTIVITIES
//    private void setupBottomNavigationView()
//    {
//        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);  //am folosit metoda asta pt a nu pune toate setup-urile aici
//        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
//    }

}
