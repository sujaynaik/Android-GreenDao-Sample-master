package ro.octa.greendaosample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ro.octa.greendaosample.adapters.UserListAdapter;
import ro.octa.greendaosample.dao.DBPhoneNumber;
import ro.octa.greendaosample.dao.DBUser;
import ro.octa.greendaosample.dao.DBUserDetails;
import ro.octa.greendaosample.manager.DatabaseManager;
import ro.octa.greendaosample.manager.IDatabaseManager;
import ro.octa.greendaosample.utils.MathUtils;

public class MainActivity extends ListActivity implements View.OnClickListener {

    private ListView list;
    private UserListAdapter adapter;
    private List<DBUser> userList;
    private EditText email, fname, lname, pnum;

    private IDatabaseManager databaseManager;
    private DBUser user;
    Button deleteUser;

    String options[] = new String[]{ "Open", "Delete"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManager = new DatabaseManager(this);

        long userId = getIntent().getLongExtra("userID", 1L);
        //long userId = getN
        Log.d("on create", String.valueOf(userId));
        if (userId != -1) {
            user = databaseManager.getUserById(userId);
        }

        email = (EditText) findViewById(R.id.email);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        pnum = (EditText) findViewById(R.id.pnum);

        deleteUser = (Button) findViewById(R.id.delete_user);

        findViewById(R.id.createUserBtn).setOnClickListener(this);

        userList = new ArrayList<DBUser>();

        list = getListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Dialog dialog = onCreateDialogSingleChoice(position);
                Log.d("on item click", "2");
                dialog.show();
                Log.d("on item click", "3");
            }
        });

        refreshUserList();
    }

    public Dialog onCreateDialogSingleChoice(final int position) {

        Log.d("on create dialog", "1");
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        Log.d("on create dialog", "2");
        dialog.setTitle("Select an Option");

        dialog.setPositiveButton("Open", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DBUser user = (DBUser) list.getItemAtPosition(position);
                if (user != null) {

                    Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
                    intent.putExtra("userID", user.getId());
                    startActivityForResult(intent, 1);
                }
            }
        }).setNegativeButton("Delete", new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                DBUser user = (DBUser) list.getItemAtPosition(position);
                if (user != null) {

                    Long userId = user.getId();
                    boolean status = DatabaseManager.getInstance(MainActivity.this).deleteUserById(userId);
                    if (status) {

                        Intent returnIntent = new Intent();
                        setResult(RESULT_OK, returnIntent);
                        Log.i(UserDetailsActivity.class.getSimpleName(), "User " + userId + " was successfully deleted from the schema!");
                        Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
                        //finish();
                    } else {

                        Toast.makeText(MainActivity.this, "ops... something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Log.d("on create dialog", "5");
        return dialog.create();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                refreshUserList();
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.global, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_all_users) {
            handleDeleteAllUsers();
            return true;
        } else if (id == R.id.truncate_all_tables) {
            handleTruncateAllTables();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleTruncateAllTables() {
        databaseManager.dropDatabase();
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    private void handleDeleteAllUsers() {
        databaseManager.deleteUsers();
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        if (databaseManager == null)
            databaseManager = new DatabaseManager(this);

        super.onRestart();
    }

    @Override
    protected void onResume() {
        // init database manager
        databaseManager = DatabaseManager.getInstance(this);

        super.onResume();
    }

    @Override
    protected void onStop() {
        if (databaseManager != null)
            databaseManager.closeDbConnections();

        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createUserBtn: {

                String vemail = email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
                if (vemail.length() > 0 && fname.length() > 0 && lname.length() > 0 && pnum.length() > 0) {

                    if (vemail.matches(emailPattern) || vemail.matches(emailPattern2)) {

                        if (pnum.length() == 10) {

                            DBUser user = new DBUser();
                            user.setEmail(email.getText().toString());
                            user.setPassword("defaultPass");

                            // insert that user object to our DB
                            user = databaseManager.insertUser(user);

                            // Create a random userDetails object
                            DBUserDetails userDetails = new DBUserDetails();
                            userDetails.setBirthDate(new Date());
                            userDetails.setRegistrationDate(new Date());
                            userDetails.setCountry("India");
                            userDetails.setFirstName(fname.getText().toString());
                            userDetails.setLastName(lname.getText().toString());
                            userDetails.setGender("MALE");
                            userDetails.setNickName(fname.getText().toString());
                            userDetails.setUserId(user.getId());
                            userDetails.setUser(user);

                            // insert or update this userDetails object to our DB
                            userDetails = databaseManager.insertOrUpdateUserDetails(userDetails);

                            // link userDetails Key to user
                            user.setDetailsId(userDetails.getId());
                            user.setDetails(userDetails);
                            databaseManager.updateUser(user);

                            // create a dynamic list of phone numbers for the above object
                            for (int i = 0; i < MathUtils.randInt(1, 7); i++) {
                                DBPhoneNumber phoneNumber = new DBPhoneNumber();
                                phoneNumber.setPhoneNumber(pnum.getText().toString());
                                phoneNumber.setDetailsId(userDetails.getId());

                                // insert or update an existing phone number into the database
                                databaseManager.insertOrUpdatePhoneNumber(phoneNumber);
                            }

                            // add the user object to the list
                            adapter.add(user);
                            adapter.notifyDataSetChanged();
                            list.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Select the last row so it will scroll into view...
                                    list.setSelection(adapter.getCount() - 1);
                                }
                            });
                            email.setText("");
                            fname.setText("");
                            lname.setText("");
                            pnum.setText("");
                            break;
                        } else {
                            Toast.makeText(this, "strength on phone number is not 10", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "email id is not vaid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "some fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
            default:
                break;
        }

    }

    private void refreshUserList() {
        userList = DatabaseManager.getInstance(this).listUsers();
        if (userList != null) {
            if (adapter == null) {
                adapter = new UserListAdapter(MainActivity.this, userList);
                list.setAdapter(adapter);
            } else {
                list.setAdapter(null);
                adapter.clear();
                adapter.addAll(userList);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
            }
        }
    }
}