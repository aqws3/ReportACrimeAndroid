package pe.edu.upc.reportacrime.Activities.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import pe.edu.upc.reportacrime.Activities.Adapters.CategoriesAdapter;
import pe.edu.upc.reportacrime.Activities.Adapters.DistrictsAdapter;
import pe.edu.upc.reportacrime.Activities.Models.Crime;
import pe.edu.upc.reportacrime.Activities.Models.District;
import pe.edu.upc.reportacrime.Activities.Models.Category;
import pe.edu.upc.reportacrime.R;

/**
 * Created by Miguel on 05/06/2015.
 */
public class ReportCrimeActivity extends Activity {


    private Crime crime;
    private ArrayList<District> districts = new ArrayList<>();
    private DistrictsAdapter mDistrictsAdapter;

    private ArrayList<Category> categories = new ArrayList<>();
    private CategoriesAdapter mCategoryAdapter;


    private EditText titleEditText;
    private EditText descriptionEditText;
    private Spinner categorySpinner;
    private Spinner districtSpinner;
    private Button reportButton;

    private double longitude;
    private double latitude;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_a_crime);
        districts.addAll(MainMenuActivity.getDistricts());
        categories.addAll(MainMenuActivity.getCategories());

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

        titleEditText = (EditText)findViewById(R.id.titleEditText);
        descriptionEditText = (EditText)findViewById(R.id.descriptionEditText);

        mDistrictsAdapter = new DistrictsAdapter(this, android.R.layout.simple_spinner_item, districts);
        districtSpinner = (Spinner)findViewById(R.id.districtsSpinner);
        districtSpinner.setAdapter(mDistrictsAdapter);

        mCategoryAdapter = new CategoriesAdapter(this, android.R.layout.simple_spinner_item, categories);
        categorySpinner = (Spinner)findViewById(R.id.categoriesSpinner);
        categorySpinner.setAdapter(mCategoryAdapter);

        reportButton = (Button)findViewById(R.id.buttonReport);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ReportCrimeActivity.this, ReportCrimeResume.class);
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", LoginActivity.getUser().getId());
                bundle.putString("name", titleEditText.getText().toString());
                bundle.putString("description", descriptionEditText.getText().toString());
                int cat_id = (int) categorySpinner.getSelectedItemId();
                bundle.putInt("category_id", cat_id);
                Category cat = (Category) categorySpinner.getSelectedItem();
                bundle.putString("category", cat.getName());
                int dis_id = (int)districtSpinner.getSelectedItemId();
                bundle.putInt("district_id", dis_id);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                i.putExtras(bundle);
                v.getContext().startActivity(i);
            }
        });

    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}