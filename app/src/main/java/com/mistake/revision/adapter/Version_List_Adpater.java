package com.mistake.revision.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.download.service.util.*;

import org.koishi.launcher.h2co3.R;

public class Version_List_Adpater extends BaseAdapter {

	protected final Context mContext;
	protected final LayoutInflater mInflater;
	private final List<VersionUtil>release;
	private final List<VersionUtil>snapshot;
	private final List<VersionUtil>old_alpha;
	private List<VersionUtil> display;
	private final Map<Integer, RelativeLayout> viewMap = new HashMap<>();
	public OnItemDepartment listener = null;
	public Version_List_Adpater(Context context,List<VersionUtil> overall ,int type){
		mContext = context;

		display= new ArrayList<>();

		release=new ArrayList<>();
		old_alpha=new ArrayList<>();
		snapshot=new ArrayList<>();

		for(VersionUtil util:overall){
			if(util.type().equals(context.getString(R.string.ver_type_release))){
				release.add(util);
			}else if(util.type().equals("snapshot")){
				snapshot.add(util);
			}else if(util.type().contains("old")){
				old_alpha.add(util);
			}
		}
		if(type==0){
			display= snapshot;
		}else if(type==1){
			display= release;
		}else if(type==2){
			display= old_alpha;
		}
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public void setType(int type){
		if(type==0){
			display= snapshot;
		}else if(type==1){
			display= release;
		}else if(type==2){
			display= old_alpha;
		}

		notifyDataSetInvalidated();

	}
	public int getCount() {
		return getList().size();
	}

	public Object getItem(int position) {
		return getList().get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	public boolean hasStableIds() {

		return false;
	}
	public boolean isEmpty() {

		return false;
	}
	public int getItemViewType(int arg0) {

		return arg0;
	}
	public boolean areAllItemsEnabled() {

		return false;
	}
	public boolean isEnabled(int arg0) {

		return true;
	}
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		final VersionUtil file = getList().get(arg0);
		final String id=file.id().toLowerCase();
		String type=file.type().toLowerCase();
		
		
		RelativeLayout madapter = this.viewMap.get(arg0);
		final ViewHolder viewh;
		if (madapter == null) {
			madapter = (RelativeLayout) mInflater.inflate(R.layout.version_item, null);
			viewh = new ViewHolder();
			viewh.item=(LinearLayout)madapter.findViewById(R.id.download_ver_item);
			viewh.idText=(TextView)madapter.findViewById(R.id.id);
			viewh.typeText=(TextView)madapter.findViewById(R.id.type);
			viewh.item.setOnClickListener(p1 -> listener.OnItemDepartmentItem(
			id
			,  null));
			madapter.setTag(viewh);
		} else {
			viewh = (ViewHolder) madapter.getTag();
		}
		viewh.idText.setText(id);
		viewh.typeText.setText(type);
		if (type.equals("release")){
			viewh.typeText.setText(R.string.download_release);
		} else if (type.equals("snapshot")){
			viewh.typeText.setText(R.string.download_snapshot);
		} else {
			viewh.typeText.setText(R.string.download_old_beta);
		}
		viewMap.put(arg0, madapter);
		return madapter;
	}
	static class ViewHolder
	{
		public LinearLayout item;
		public TextView idText,typeText;

	}
	protected List<VersionUtil> getList() {
		return display;
	}
	public interface OnItemDepartment{
		void OnItemDepartmentItem(String type);
		void OnItemDepartmentItem(String id,String url);


	}

	public void setOnItemDepartment(OnItemDepartment onItemDepartment) {
		this.listener = onItemDepartment;
	}

}

