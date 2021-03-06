package cmovil.gr7.rapidturns;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AgregarServicio extends Activity {
    private Button add;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        add = (Button) findViewById(R.id.create);
        name = (EditText) findViewById(R.id.name);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if ((ni != null) && (ni.isConnected())) {


                    String Name = name.getText() + "";
                    if (Name.equals("")) {
                        Error(R.string.error_field_required);
                    } else {
                        final ProgressDialog dialog = new ProgressDialog(AgregarServicio.this);
                        dialog.setMessage("Posting...");
                        dialog.show();

                        ParseObject Lunes = new ParseObject("Dia");
                        ParseACL acl1 = new ParseACL();
                        acl1.setPublicReadAccess(true);
                        acl1.setPublicWriteAccess(true);
                        Lunes.setACL(acl1);

                        ParseObject Martes = new ParseObject("Dia");
                        ParseACL acl2 = new ParseACL();
                        acl2.setPublicReadAccess(true);
                        acl2.setPublicWriteAccess(true);
                        Martes.setACL(acl2);

                        ParseObject Miercoles = new ParseObject("Dia");
                        ParseACL acl3 = new ParseACL();
                        acl3.setPublicReadAccess(true);
                        acl3.setPublicWriteAccess(true);
                        Miercoles.setACL(acl3);

                        ParseObject Jueves = new ParseObject("Dia");
                        ParseACL acl4 = new ParseACL();
                        acl4.setPublicReadAccess(true);
                        acl4.setPublicWriteAccess(true);
                        Jueves.setACL(acl4);

                        ParseObject Viernes = new ParseObject("Dia");
                        ParseACL acl5 = new ParseACL();
                        acl5.setPublicReadAccess(true);
                        acl5.setPublicWriteAccess(true);
                        Viernes.setACL(acl5);

                        ParseObject Sabado = new ParseObject("Dia");
                        ParseACL acl6 = new ParseACL();
                        acl6.setPublicReadAccess(true);
                        acl6.setPublicWriteAccess(true);
                        Sabado.setACL(acl6);

                        ParseObject Domingo = new ParseObject("Dia");
                        ParseACL acl7 = new ParseACL();
                        acl7.setPublicReadAccess(true);
                        acl7.setPublicWriteAccess(true);
                        Domingo.setACL(acl7);

                        ParseObject semana = new ParseObject("Semana");
                        semana.put("Lunes", Lunes);
                        semana.put("Martes", Martes);
                        semana.put("Miercoles", Miercoles);
                        semana.put("Jueves", Jueves);
                        semana.put("Viernes", Viernes);
                        semana.put("Sabado", Sabado);
                        semana.put("Domingo", Domingo);
                        ParseACL acl8 = new ParseACL();
                        acl8.setPublicReadAccess(true);
                        acl8.setPublicWriteAccess(true);
                        semana.setACL(acl8);

                        ParseObject values = new ParseObject("Servicio");
                        values.put(Contract.Column.NAME, Name);
                        values.put(Contract.Column.HORARIO, semana);
                        values.put("local", ParseUser.getCurrentUser());
                        ParseACL acl = new ParseACL();
                        acl.setPublicReadAccess(true);
                        acl.setPublicWriteAccess(true);
                        values.setACL(acl);
                        values.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "NO INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void Error (int a){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(a)
                .setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
