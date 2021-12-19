package com.example.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {
    private Context context;
    private List<StudentModel> list;

    public Adapter(Context context, List<StudentModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Viewholder holder, int position) {
        holder.id.setText(String.valueOf(list.get(position).getId()));
        holder.name.setText(list.get(position).getName());
        holder.age.setText(String.valueOf(list.get(position).getAge()));
        holder.activity.setText(list.get(position).isActive() ? "True" : "False");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;
        private TextView age;
        private TextView activity;
        private Button update;
        private Button delete;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            activity = itemView.findViewById(R.id.isActive);
            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView idMain = (TextView) ((Activity) context).findViewById(R.id.idMain);
                    EditText editName = (EditText) ((Activity) context).findViewById(R.id.editTextName);
                    EditText editAge = (EditText) ((Activity) context).findViewById(R.id.editTextAge);
                    Switch switchIsActive = (Switch) ((Activity) context).findViewById(R.id.switchStudent);
                    Button updateMain = (Button) ((Activity) context).findViewById(R.id.buttonAdd);
                    updateMain.setText("Update");
                    idMain.setText(id.getText());
                    editName.setText(name.getText());
                    editAge.setText(age.getText());
                    Log.i("SWITCH", activity.getText().toString());
                    switchIsActive.setChecked(activity.getText().toString() == "True");
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBHelper dbHelper = new DBHelper(context);
                    if (dbHelper.deleteStudent(Integer.valueOf(id.getText().toString()))) {
                        ((MainActivity) context).seeAllRecords();
                    }

                }
            });
        }
    }

}
