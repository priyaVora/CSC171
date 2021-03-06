package com.example.priya.socialmediaapp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.priya.socialmediaapp.Activities.Camera.MainCameraActivity;
import com.example.priya.socialmediaapp.Activities.Camera.RunTimePermission;
import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Alphatical_Order;
import com.example.priya.socialmediaapp.ChatApplication.Chat_model.Contact;
import com.example.priya.socialmediaapp.ChatApplication.Chat_model.User;
import com.example.priya.socialmediaapp.Model.tab_calls;
import com.example.priya.socialmediaapp.Model.tab_contacts;
import com.example.priya.socialmediaapp.Model.tab_chat;
import com.example.priya.socialmediaapp.Model.tab_status;
import com.example.priya.socialmediaapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainContentTabbedActivity extends AppCompatActivity {

    private List<Contact> listofContacts;
    private List<String> phone_number_list;
    private ProgressDialog mGrabbContactDialog;

    private DatabaseReference mDatabaseReferences;
    private DatabaseReference userDatabaseReferences;
    private FirebaseDatabase mDatabase;
    private StorageReference mFirebaseStorage;
    private String current_channel_for_user;

    private RunTimePermission runTimePermission;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Menu originalMenu;

    private tab_calls tab4;
    private final static int GALLERYCODE = 1;
    private Uri resultUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content_tabbed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listofContacts = new ArrayList<>();
        phone_number_list = new ArrayList<>();
        tab4 = new tab_calls();

        mGrabbContactDialog = new ProgressDialog(MainContentTabbedActivity.this);
        mGrabbContactDialog.setMessage("Grabbing device contacts...");


        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReferences = mDatabase.getReference().child("MContacts");
        userDatabaseReferences = mDatabase.getReference().child("MUser");
        mFirebaseStorage = FirebaseStorage.getInstance().getReference().child("MContact_Profile_Pics");
        mAuth = FirebaseAuth.getInstance();




        runTimePermission = new RunTimePermission(MainContentTabbedActivity.this);
        runTimePermission.requestPermission(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.CALL_PHONE
        }, new RunTimePermission.RunTimePermissionListener() {

            @Override
            public void permissionGranted() {
                Toast.makeText(MainContentTabbedActivity.this, "Permission is granted...", Toast.LENGTH_LONG);
            }

            @Override
            public void permissionDenied() {

            }
        });

        userDatabaseReferences.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String userid = mAuth.getCurrentUser().getUid();
                   User current_user = dataSnapshot.getValue(User.class);
                   Log.d("result_current_user",""+ current_user.getFirstname());
                Log.d("result_current_user",""+ userid);
                Log.d("result_current_user",""+ dataSnapshot.getKey().equals(userid));
                if(dataSnapshot.getKey().equals(userid)) {
                    current_channel_for_user = current_user.getChannel();
                }

                Log.d("result_current_user",""+ current_channel_for_user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.camera));
        tabLayout.getTabAt(0).setText("");
        tabLayout.addTab(tabLayout.newTab().setText("CHATS"));
        tabLayout.addTab(tabLayout.newTab().setText("STATUS"));
        tabLayout.addTab(tabLayout.newTab().setText("CALLS"));


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2) {
                    if(originalMenu != null) {
                        originalMenu.clear();
                        getMenuInflater().inflate(R.menu.status_menu, originalMenu);
                    }

                } else if(position == 3) {
                    if(originalMenu != null) {
                        originalMenu.clear();

                        getMenuInflater().inflate(R.menu.calls_menu, originalMenu);
                    }
                } else if(position == 1) {
                    if(originalMenu != null) {
                        originalMenu.clear();

                        getMenuInflater().inflate(R.menu.main_menu, originalMenu);
                    }
                } else {
                    if(originalMenu != null) {
                        originalMenu.clear();
                        getMenuInflater().inflate(R.menu.main_menu, originalMenu);
                    }
                }

                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewPager.getCurrentItem() != 3) {
                   /// startActivity(new Intent(MainContentTabbedActivity.this, ChatActivity.class));
                    Intent video_call_intent = new Intent(MainContentTabbedActivity.this, VideoChatViewActivity.class);
                    video_call_intent.putExtra("channel_id","" + current_channel_for_user);
                    startActivity(video_call_intent);
                    finish();
                } else {
                    String userid = mAuth.getCurrentUser().getUid();
                    mDatabaseReferences.child(userid).removeValue();
                    final DatabaseReference currentUserDb = mDatabaseReferences.child(userid);
                    sync_contacts(currentUserDb);
                    tab4.setListofContacts(new ArrayList<Contact>());
                    tab4.return_adapter().notifyDataSetChanged();
                    listofContacts = new ArrayList<>();

                }
            }
        });
        mViewPager.setCurrentItem(1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("CHATS")) {
                    mViewPager.setCurrentItem(1);
                }  else if(tab.getText().equals("STATUS")) {
                    mViewPager.setCurrentItem(2);
                } else if(tab.getText().equals("CALLS")) {
                    mViewPager.setCurrentItem(3);
                    change_button_icon(fab);
                    String userid = mAuth.getCurrentUser().getUid();
                    final DatabaseReference currentUserDb = mDatabaseReferences.child(userid);
                    tab4.setListofContacts(new ArrayList<Contact>());
                    tab4.return_adapter().notifyDataSetChanged();
                    listofContacts = new ArrayList<>();
                    phone_number_list = new ArrayList<>();

                    grabb_stored_contacts();

                } else if(tab.getText().equals("")) {
                    // mViewPager.setCurrentItem(0);
                    startActivity(new Intent(MainContentTabbedActivity.this, MainCameraActivity.class));
                    finish();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void change_button_icon(FloatingActionButton fab) {
        fab.setBackgroundResource(R.drawable.add_calling_icon);

    }

    public void clearView() {

        String userid = mAuth.getCurrentUser().getUid();

        final DatabaseReference currentUserDb = mDatabaseReferences.child(userid);
        Log.d("error_check_global_list", "" +listofContacts.size());
        Log.d("error_check_phone_list", ""+ phone_number_list.size());
        Log.d("error_check_tab4", "" +tab4.getListofContacts().size());

        for(int i = 0; i < phone_number_list.size(); i++) {
            Log.d("error_phone_check ", "" + phone_number_list.get(i));
        }

        grabb_stored_contacts();
        Log.d("error", "after grabb contacts");
        Log.d("error_check_global_list", "" +listofContacts.size());
        Log.d("error_check_phone_list", ""+ phone_number_list.size());
        Log.d("error_check_tab4", "" +tab4.getListofContacts().size());

    }

    private void sync_contacts(DatabaseReference currentUserDb) {
        mGrabbContactDialog.show();
        listofContacts = new ArrayList<>();
        getContactList();
        Alphatical_Order order = new Alphatical_Order();
        listofContacts = order.sort(listofContacts);
        tab4.setListofContacts(new ArrayList<Contact>());
        tab4.return_adapter().notifyDataSetChanged();

        if(listofContacts.size() > 1) {
            for (int i = 0; i < listofContacts.size(); i++) {
                Uri mImageUri = Uri.parse(listofContacts.get(i).getProfileImage());
                StorageReference filepath = mFirebaseStorage.child("MContact_Profile").child(mImageUri.getLastPathSegment());
                final int count = i;
                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        DatabaseReference newPost = mDatabaseReferences.push();

                        if (listofContacts.size() != 0) {
                            if (listofContacts.size() != count) {
                                if (count < listofContacts.size()) {
                                    listofContacts.get(count).setProfileImage(downloadUrl.toString());
                                }
                            }
                        }
                    }
                });
                currentUserDb.child("contact_" + i).setValue(listofContacts.get(i));
            }
            Toast.makeText(MainContentTabbedActivity.this, "Synced Contacts", Toast.LENGTH_LONG).show();
            mGrabbContactDialog.dismiss();
        }
        clearView();
    }

    long return_value = 0;


    public long grabb_stored_contacts() {
        if(listofContacts == null) {

            listofContacts = new ArrayList<>();

        }
        tab4.setListofContacts(new ArrayList<Contact>());
        Log.d("grabbed_contacts","" + tab4.getListofContacts().size());
        tab4.return_adapter().notifyDataSetChanged();
        mDatabaseReferences.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                long count_for_contact = dataSnapshot.getChildrenCount();

                Iterable<DataSnapshot> list = dataSnapshot.getChildren();

                for(DataSnapshot d : list) {
                    Log.d("SN", d.getValue().toString());
                }
                for (int i = 0; i < count_for_contact; i++) {
                    HashMap<String, Contact> current_contact = (HashMap<String, Contact>) dataSnapshot.child("contact_" + i).getValue();

                    Log.d("Grabbed Contact", "Current Contact: " + current_contact.toString());


                    Contact new_contact = new Contact();

                    for (Map.Entry e : current_contact.entrySet()) {
                        Log.d("Key: ", e.getKey().toString());
                        Log.d("Value: ", e.getValue().toString());

                        if (e.getKey().equals("name")) {
                            new_contact.setName(e.getValue().toString());
                        } else if (e.getKey().equals("phone_number")) {
                            new_contact.setPhone_number(e.getValue().toString());
                        } else if (e.getKey().equals("profileImage")) {
                            new_contact.setProfileImage(e.getValue().toString());
                        }
                        Log.d("Contact String: ", new_contact.toString());

                        if(!listofContacts.contains(new_contact)) {
                            listofContacts.add(new_contact);
                        }
                        Log.d("Logged Value--", "" + return_value);

                        return_value = count_for_contact;
                    }


                }
                if(return_value > 0) {
                    tab4.setListofContacts(listofContacts);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return return_value;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        originalMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_signout) {
            mAuth.signOut();
            startActivity(new Intent(MainContentTabbedActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public static Bitmap retrieveContactPhoto(Context context, String number) {
        ContentResolver contentResolver = context.getContentResolver();

        String contactId = null;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID};

        Cursor cursor =
                contentResolver.query(
                        uri,
                        projection,
                        null,
                        null,
                        null);
        //   Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
            }
            cursor.close();
        }

        Bitmap photo = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.white_circle);

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactId)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            }

            assert inputStream != null;
            if(inputStream != null) {
                inputStream.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        phone_number_list = new ArrayList<>();

        if ((cur != null ? cur.getCount() : 0) > 0) {
            int counter = 0;
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));


                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("CONTACT from call", "Name: " + name);
                        Log.i("CONTACT from call", "Phone Number: " + phoneNo);

                        Contact newContact = new Contact();
                        newContact.setName(name);
                        newContact.setPhone_number(phoneNo);

                        Bitmap d =  retrieveContactPhoto(MainContentTabbedActivity.this, newContact.getPhone_number());

                        Uri profile_uri = getImageUri(MainContentTabbedActivity.this, d);

                        newContact.setProfileImage(profile_uri.toString());
                        String test_number = getSimplifiedNumber(newContact.getPhone_number());
                        if(!phone_number_list.contains(test_number)) {
                            phone_number_list.add(test_number);
                            if(!listofContacts.contains(newContact)) {
                                listofContacts.add(counter, newContact);
                            }
                            counter++;
                        } else {
                            Log.d("CONTACT from call","ALREADY IN LIST... " + test_number);
                        }
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getSimplifiedNumber(String phone_number) {
        phone_number = phone_number.replace("+", "");
        phone_number = phone_number.replace("(", "");
        phone_number = phone_number.replace(")", "");
        phone_number = phone_number.replace("-", "");
        phone_number = phone_number.replace(" ", "");
        Log.d("CONTACT from call", phone_number);
        return phone_number;
    }

    //deleted PlaceHoldeFragment class from here.

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    tab_contacts tab1 = new tab_contacts();
                    return tab1;
                case 1:
                    tab_chat tab2 = new tab_chat();
                    return tab2;
                case 2:
                    tab_status tab3 = new tab_status();
                    return tab3;
                case 3:
                    if(tab4 == null) {
                        tab4 = new tab_calls();
                    }
                    return tab4;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CAMERA";
                case 1:

                    return "CHATS";
                case 2:
                    return "STATUS";
                case 3:
                    return "CALLS";
            }
            return null;
        }
    }
}