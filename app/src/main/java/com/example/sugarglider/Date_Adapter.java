package com.example.sugarglider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.litepal.crud.DataSupport;
import java.util.AbstractCollection;
import java.util.List;


public class Date_Adapter extends RecyclerView.Adapter<Date_Adapter.ViewHolder>{

    private List<Events> mDateList;
    private Context mContext;
    private LayoutInflater inflater;
    private AbstractCollection infos;

    public interface OnItemOnClickListener{
        void onItemOnClick(View view,int pos);
        void onItemLongOnClick(View view ,int pos);
    }
    private OnItemOnClickListener mOnItemOnClickListener;
    public void setOnItemClickListener(OnItemOnClickListener listener){
        this.mOnItemOnClickListener = listener;
    }
    public void removeItem(int pos){
        infos.remove(pos);
        notifyItemRemoved(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View DateView;
        TextView DateName;
        TextView DatemYear;
        TextView DatemMonth;
        TextView DatemDay;
        TextView Datenum;


        public ViewHolder(View view) {
            super(view);
            DateView = view;
            DateName = (TextView) view.findViewById(R.id.item_name);
            DatemYear = (TextView) view.findViewById(R.id.item_mYear);
            DatemMonth = (TextView)view.findViewById(R.id.item_mMonth);
            DatemDay = (TextView)view.findViewById(R.id.item_mDay);
            Datenum = (TextView)view.findViewById(R.id.item_num);

        }

    }

    public Date_Adapter(List<Events> fruitList,Context context) {
        mDateList = fruitList;
        mContext=context;
    }

    @Override
    public int getItemViewType(int position) {
        return mDateList.get(position).getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
            View viewone = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surplus, parent, false);
            ViewHolder holderone = new ViewHolder(viewone);
            return holderone;
        }
        if (viewType==2){
            View viewtwo = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_total, parent, false);

            ViewHolder holdertwo = new ViewHolder(viewtwo);
            return holdertwo;
        }
        if (viewType==3){
            View viewthree = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_birthday, parent, false);

            ViewHolder holderthree = new ViewHolder(viewthree);
            return holderthree;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Events fruit = mDateList.get(position);
        holder.DateName.setText(fruit.getName());
        holder.DatemYear.setText(fruit.getmYear());
        holder.DatemMonth.setText(fruit.getmMonth());
        holder.DatemDay.setText(fruit.getmDay());
        int k;
        long birthday;
        int type = fruit.getType();
        final String in_ymd = fruit.getmYear()+"-"+fruit.getmMonth()+"-"+fruit.getmDay();
        String in_mmdd = fruit.getmMonth()+"-"+fruit.getmDay();
        String beforedate = num_operate.getToday();
        String mmdd = num_operate.getTodaytwo();
        long minus_day = num_operate.dateDiff(beforedate,in_ymd,"yyyy-MM-dd");
        long total_day = num_operate.dateDiff(in_ymd,beforedate,"yyyy-MM-dd");
        long diff = Math.abs(num_operate.dateDiff(mmdd,in_mmdd,"MM-dd"));
        if(((Integer.parseInt(num_operate.getTodayyear())+1)%4 == 0)&&((Integer.parseInt(num_operate.getTodayyear())+1)%100 != 0)||((Integer.parseInt(num_operate.getTodayyear())+1)%400 == 0)){ k = 366; }//判断生日天数
        else { k = 365; }
        if(Integer.parseInt(fruit.getmMonth()) == Integer.parseInt(num_operate.getTodaymonth())){
            if(Integer.parseInt(num_operate.getTodayday()) >= Integer.parseInt(fruit.getmDay())){ birthday = diff; }
            else{ birthday = k-diff; }
        }
        else if(Integer.parseInt(num_operate.getTodaymonth())>Integer.parseInt(fruit.getmMonth())){ birthday = k-diff; }
        else{ birthday = diff; }//判断生日天数
        if(type==1){
            holder.Datenum.setText(minus_day+"");
        }
        if (type==2){
            holder.Datenum.setText(total_day+"");
        }
        if (type==3){
            holder.Datenum.setText(birthday+"");
        }


        if(mOnItemOnClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(mContext,Add_Activity.class);
                    intent.putExtra("flag","0");//flag标识
                    intent.putExtra("type",fruit.getType());
                    intent.putExtra("back_id",fruit.getId());
                    intent.putExtra("back_name",fruit.getName());
                    intent.putExtra("back_mYear",fruit.getmYear());
                    intent.putExtra("back_mMonth",fruit.getmMonth());
                    intent.putExtra("back_mDay",fruit.getmDay());
                    mContext.startActivity(intent);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //弹出一个对话选择框
                    Vibrator vibrator = (Vibrator)mContext.getSystemService(mContext.VIBRATOR_SERVICE);
                    vibrator.vibrate(20);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("Warning");
                    dialog.setMessage("确定要删除吗?");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataSupport.deleteAll(Events.class,"name = ?",fruit.getName());//删除
                            mDateList.remove(position);//刷新列表
                            notifyDataSetChanged();//刷新列表
                        }
                    });
                    dialog.setNegativeButton("点错了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                    return true;
                }


            });
        }
    }
    @Override
    public int getItemCount() {
        return mDateList.size();
    }
}
