package cmovil.gr7.rapidturns;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarServicios extends Fragment {
    private Object[][] records;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView lista;
    private Button add;
    private int mCurrentSelectedPosition=0;

    public static MostrarServicios newInstance(int sectionNumber) {
        MostrarServicios fragment = new MostrarServicios();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MostrarServicios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listalocal, container, false);
        lista = (ListView) v.findViewById(R.id.ListView);
        records();
        lista.setAdapter(new AdapterServicios(
                getActivity().getActionBar().getThemedContext(),
                records,"#ffffff"));
        lista.setItemChecked(mCurrentSelectedPosition, true);
        add = (Button) v.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity().getActionBar().getThemedContext(),AgregarServicio.class);
                startActivity(intent);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object[] o = (Object[]) lista.getItemAtPosition(position);
                String str = (String) o[0];//As you are using Default String Adapter
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LocalActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


    public void records() {

        String NAME = Contract.Column.NAME;
        String HORARIO = Contract.Column.HORARIO;
        String CREATED_AT = Contract.Column.CREATED_AT;
        String FROM = Contract.Column.FROM;
        String TO = Contract.Column.TO;
        String ID = Contract.Column.ID;

        DbHelper dbHelper= new DbHelper(getActivity().getActionBar().getThemedContext());//Instancia de DbHelper
        SQLiteDatabase db=dbHelper.getWritableDatabase();//Obtener instancia de BD

        Cursor cursor = db.query(Contract.SERVICIO, null,null, null, null, null, null);
        records = new Object[cursor.getCount()][3];
        int i=0;
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(NAME));
                String hora = cursor.getString(cursor.getColumnIndex(HORARIO));
                String created_at = cursor.getString(cursor.getColumnIndex(CREATED_AT));
                int from = cursor.getInt(cursor.getColumnIndex(FROM));
                int to = cursor.getInt(cursor.getColumnIndex(TO));
                int id = cursor.getPosition();

                records[i][0] = name;
                records[i][1] = hora+" "+getString(R.string.from)+" "+from+" "+getString(R.string.to)+" "+to;
                records[i][2] = created_at;

                i++;
            }
        }else{
            Log.d("ContentProvider", "Cursor:" + cursor.getCount());

        }
    }

}