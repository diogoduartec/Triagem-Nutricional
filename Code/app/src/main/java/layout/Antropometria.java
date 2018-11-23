package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.infokmg.hugv.triagemnutricional.DAO.FirebaseConfig;
import com.infokmg.hugv.triagemnutricional.MainActivity;
import com.infokmg.hugv.triagemnutricional.R;
import com.infokmg.hugv.triagemnutricional.layout.expansive.UpdateDataListener;
import com.infokmg.hugv.triagemnutricional.model.UserData;
import com.infokmg.hugv.triagemnutricional.service.MainConfigConstants;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Antropometria.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Antropometria#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Antropometria extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM_ACTIVITY = "activityAtropometria";
    private static final String TAG = "EXCEL";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserData patient;
    private DatabaseReference databaseReference;
    private String id;
    UpdateDataListener updateDataListener;

    Button btFinalize;

    private OnFragmentInteractionListener mListener;

    public Antropometria() {
        // Required empty public constructor
    }

    public Antropometria(MainActivity mainActivity, UserData userData, String id) {
        updateDataListener = mainActivity;
        this.patient = userData;
        this.id = id;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Antropometria.
     */
    // TODO: Rename and change types and number of parameters
    public static Antropometria newInstance(MainActivity mainActivity) {
        Antropometria fragment = new Antropometria();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACTIVITY, mainActivity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            updateDataListener = (UpdateDataListener) getArguments().getSerializable(ARG_PARAM_ACTIVITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_antropometria, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btFinalize = (Button) view.findViewById(R.id.bt_finalize);

        btFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick finalizar" );
                btFinalize.setEnabled(false);

                sendPatientData(patient);

                //Toast.makeText(getContext(), "Nome: "+userData.getName(), Toast.LENGTH_SHORT).show();

                /*try{
                    Toast.makeText(getContext(), "Armazenando triagem...", Toast.LENGTH_LONG).show();
                    String fileNameXls = getPathFileNameXls();
                    UserData data = updateDataListener.getData();
                    updateNutritionalSpreadSheet(fileNameXls, fileNameXls, data);
                    Toast.makeText(getContext(), "Triagem armazenada", Toast.LENGTH_LONG).show();
                }catch (IOException io){
                    io.printStackTrace();
                    Toast.makeText(getContext(), "ERRO AO FINALIZAR TRIAGEM", Toast.LENGTH_LONG).show();
                }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(getContext(), "ERRO AO FINALIZAR TRIAGEM", Toast.LENGTH_LONG).show();
                }*/
                btFinalize.setEnabled(true);
                getActivity().finish();


            }

        });

        btFinalize.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //  readExcelFile(getContext(),"myExcel.xls");
                return false;
            }
        });
    }

    private boolean sendPatientData(UserData pPatient){
        try{
            databaseReference = FirebaseConfig.getDatabaseReference().child("nutricionists").child(id).child("patients");
            databaseReference.child(pPatient.getName()).setValue(pPatient);
            Toast.makeText(getContext(), "Enviado", Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            Toast.makeText(getContext(), "Erro ao enviar", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private String getPathFileNameXls() throws IOException {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "HUGV");
        Log.d("PATH", f.getAbsolutePath());
        if (!f.exists()) {
            Log.d("MAKE DIR", f.mkdirs() + "");
        }

        try {
            File file = new File(f.getAbsolutePath(), MainConfigConstants.XLSX_SHEET_NAME);
            if(!file.exists()){
                file.createNewFile();
                Log.d("create file", file.getAbsolutePath() + "");
            }
            return file.getAbsolutePath();
        }catch (IOException io){
            io.printStackTrace();
            Toast.makeText(getContext(), "ERRO AO CRIAR PLANILHA", Toast.LENGTH_LONG).show();
            throw new IOException();
        }
    }


    private XSSFWorkbook readExcelFile(String filename) throws Exception {
        FileInputStream fis = new FileInputStream(filename);
        try {
            return new XSSFWorkbook(fis);        // NOSONAR - should not be closed here
        }catch (Exception ex){
            ex.printStackTrace();
            throw new Exception(ex);
        } finally {
            fis.close();
        }
    }



    private void updateNutritionalSpreadSheet(String fileName, String fileNameOutput, UserData userData) throws Exception{
        XSSFWorkbook wb = readExcelFile(fileName);
        try {
            XSSFSheet sheet = wb.getSheetAt(0);

            Row row = sheet.createRow(sheet.getPhysicalNumberOfRows()+1);
            CellStyle cs = wb.createCellStyle();
            cs.setBorderBottom(CellStyle.BORDER_THIN);
            cs.setBorderTop(CellStyle.BORDER_THIN);
            cs.setBorderLeft(CellStyle.BORDER_THIN);
            cs.setBorderRight(CellStyle.BORDER_THIN);
            Cell cell = row.createCell(0);
            cell.setCellValue("");
            cell.setCellStyle(cs);

            cell = row.createCell(1);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            cell.setCellValue(df.format(userData.getDateCreated()));
            cell.setCellStyle(cs);

            cell = row.createCell(2);
            cell.setCellValue(userData.getRoom() == null ? "" : String.valueOf(userData.getRoom()));
            cell.setCellStyle(cs);

            cell = row.createCell(3);
            cell.setCellValue(userData.getName() == null ? "" : userData.getName());
            cell.setCellStyle(cs);

            //sexo
            cell = row.createCell(4);
            cell.setCellValue(userData.getSex()==null ? "" : userData.getSex());
            cell.setCellStyle(cs);

            cell = row.createCell(5);
            cell.setCellValue(userData.getAge() == null ? 0 : userData.getAge());
            cell.setCellStyle(cs);

            cell = row.createCell(6);
            cell.setCellValue(userData.getActualWeigth() == null ? "" : String.valueOf(userData.getActualWeigth()));
            cell.setCellStyle(cs);

            cell = row.createCell(7);
            cell.setCellValue(userData.getUsualWeigth() == null ? "" : String.valueOf(userData.getUsualWeigth()) );
            cell.setCellStyle(cs);

            cell = row.createCell(8);
            cell.setCellValue(userData.getHeigth() == null ? "" : String.valueOf(userData.getHeigth()));
            cell.setCellStyle(cs);

            cell = row.createCell(9);
            cell.setCellValue(userData.getIMC()== null ? "" : String.format("%.2f", userData.getIMC()));
            cell.setCellStyle(cs);

            //perda de peso
            cell = row.createCell(10);
            cell.setCellValue("");
            cell.setCellStyle(cs);

            cell = row.createCell(11);
            cell.setCellValue(userData.getIMCDiagnostic() == null ? "" : userData.getIMCDiagnostic());
            cell.setCellStyle(cs);

            cell = row.createCell(12);
            cell.setCellValue(userData.getMedicalDiegnostic()  == null ? "" : userData.getMedicalDiegnostic());
            cell.setCellStyle(cs);

            //doenca base
            cell = row.createCell(13);
            cell.setCellValue("");
            cell.setCellStyle(cs);

            //apenas setando o estilo para as células ficarem com borda
            for (int i=14;i<=26;i++){
                cell = row.createCell(i);
                cell.setCellStyle(cs);
            }

            //score NRS
            cell = row.createCell(27);
            cell.setCellValue(userData.getScore() == null ? 0 : userData.getScore());
            cell.setCellStyle(cs);


            FileOutputStream stream = new FileOutputStream(fileNameOutput);
            try {
                wb.write(stream);
            } finally {
                stream.close();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }


/*
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static void readExcelFile(Context context, String filename) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return;
        }

        try {
            // Creating Input Stream
            File file = new File(context.getExternalFilesDir(null), filename);
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);


            Iterator rowIter = mySheet.rowIterator();

            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    Log.d(TAG, "Cell Value: " + myCell.toString());
                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    private static boolean saveExcelFile(Context context, String fileName) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("myOrder");

        // Generate column headings
        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("Item Number");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("Quantity");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("Price");
        c.setCellStyle(cs);


        for (int i=1; i<9; i++){
            row = sheet1.createRow(i);

            c = row.createCell(0);
            c.setCellValue("Item Number");
            c.setCellStyle(cs);

            c = row.createCell(1);
            c.setCellValue("Quantity");
            c.setCellStyle(cs);

            c = row.createCell(2);
            c.setCellValue("Price");
            c.setCellStyle(cs);
        }

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));

        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
