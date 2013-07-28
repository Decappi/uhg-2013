package tu.kom.uhg;

import java.util.LinkedList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MeActivity extends GenericActivity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_me);
	    Toast.makeText(this, "me activity loaded", Toast.LENGTH_LONG).show();
	    SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
	
	    //Set the data for the text on the left of avatar
	    String userSex = prefs.getString("user_sex", "keine Angabe");
	    Integer userAge = prefs.getInt("user_age", 25);
	    Integer userHeight = prefs.getInt("user_height", 175);
	    Integer userWeight = prefs.getInt("user_weight", 70);
	    
	    Integer userBMI = (userWeight*10000/(userHeight*userHeight));
	    
        final List<String[]> list = new LinkedList<String[]>();
        list.add(new String[]{userSex, "Geschlecht"});
        list.add(new String[]{userAge.toString(), "Alter"});
        list.add(new String[]{userHeight.toString(), "Groesse"});
        list.add(new String[]{userWeight.toString(), "Gewicht"});
        list.add(new String[]{userBMI.toString(), "BMI"});

        ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this, android.R.layout.simple_list_item_2, android.R.id.text1, list){

            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);

                String[] entry = list.get(position);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(entry[0]);
                text2.setText(entry[1]);

                return view;
            }
        };

        ListView userData = (ListView)findViewById(R.id.userData);  //use the id specified in the layout file
        userData.setAdapter(adapter);

        //Set name of the user
        String userName = prefs.getString("user_name", "Xena die Kriegerprinzessin");
        TextView userNameText = (TextView)findViewById(R.id.userName);
        userNameText.setText(userName);
    }
	
	public void editMe(View v) {	
		//переписать функионал дл€ всех полей в новую функцию "ќк"
		//едитћ» должна кативировать едит“екст пол€ дл€ перен€ти€ данных
		
		findViewById(R.id.mainMe).setVisibility(View.GONE);
		findViewById(R.id.editableMe).setVisibility(View.VISIBLE);
		
	}
	
	public void acceptChanges(View v) {
		//read new values
		EditText sexET = (EditText)findViewById(R.id.editSex);
		EditText ageET = (EditText)findViewById(R.id.editAge);
		EditText heightET = (EditText)findViewById(R.id.editHeight);
		EditText weightET = (EditText)findViewById(R.id.editWeight);
		//temp vars for the entered values
		String newSex = sexET.getText().toString();
		Integer newAge = Integer.parseInt(ageET.getText().toString());
		Integer newHeight = Integer.parseInt(heightET.getText().toString());
		Integer newWeight = Integer.parseInt(weightET.getText().toString());
		//save values to the preferences
		SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
	    editor.putString("user_sex", newSex);
	    editor.putInt("user_age", newAge);
	    editor.putInt("user_height", newHeight);
	    editor.putInt("user_weight", newWeight);
	    editor.commit();
		//TODO set updated values to View
	    Integer newBMI;
	    if (newHeight != 0)
	    	newBMI = (newWeight*10000/(newHeight*newHeight));
	    else
	    	newBMI = 0;
	    final List<String[]> list = new LinkedList<String[]>();
        list.add(new String[]{newSex, "Geschlecht"});
        list.add(new String[]{newAge.toString(), "Alter"});
        list.add(new String[]{newHeight.toString(), "Groesse"});
        list.add(new String[]{newWeight.toString(), "Gewicht"});
        list.add(new String[]{newBMI.toString(), "BMI"});

        ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this, android.R.layout.simple_list_item_2, android.R.id.text1, list){

            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);

                String[] entry = list.get(position);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(entry[0]);
                text2.setText(entry[1]);

                return view;
            }
        };        
        ListView userData = (ListView)findViewById(R.id.userData);  //use the id specified in the layout file
        userData.setAdapter(adapter);
	    
	    
	    /*TextView userNameText = (TextView)findViewById(R.id.userName);
        //userNameText.setText(newName);*/	
        
        //hide editableMe, show VisibleMe with updated values
        findViewById(R.id.mainMe).setVisibility(View.VISIBLE);
		findViewById(R.id.editableMe).setVisibility(View.GONE);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	
	public void onListItemClick(ListView parent, View v, int position, long id) { 
		//Toast.makeText(getApplicationContext(), "You have selected"
        //        +(position+1)+"th item",  Toast.LENGTH_SHORT).show();
	    Toast.makeText(this, "list clicked", Toast.LENGTH_LONG).show();
	}
	
	
}
