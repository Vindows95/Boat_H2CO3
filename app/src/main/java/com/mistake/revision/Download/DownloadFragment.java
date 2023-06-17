package com.mistake.revision.Download;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.download.service.util.*;
import java.util.*;
import android.widget.*;
import android.graphics.*;
import android.os.*;
import java.io.*;
import com.alibaba.fastjson.*;
import com.download.service.downloader.*;
import com.google.gson.*;
import okhttp3.*;
import com.google.gson.reflect.*;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.koishi.launcher.h2co3.R;

public class DownloadFragment extends DialogFragment
{

	
	private  DownloadManager mDownloadManager;

	private ArrayList<LibrariesUtil>libraries;
	private ArrayList<AssetsUtil>assets;

	private TextView mlist;
	private TextView mpath1,mpath2, mpath3 ,mpath4;
	private ProgressBar mpath_progress1, mpath_progress2, mpath_progress3 ,mpath_progress4;
	private TextView stages_progress;

	private TextView title;

	private ImageButton pause_start1,pause_start2,pause_start3 ,pause_start4;
	
	private Button btnCancel;
	private static String
	API_Version_client_server_json;
	private static String API_Assets;
	private static String API_Libraries;

	private static String 
	game_directory;

	private static String 
	Version,
	Version_assets,
	Version_json,
	Version_jar,
	//Version_url,
	Version_libraries;
	private String assets_id;
	private String Txt;
	
	private  ArrayList<Object> Temporary;//临时数据

	private int libraries_loader_size=0;
	private int libraries_loading_size=0;
	private int assets_loader_size=0;
	private int assets_loading_size=0;
	
	private boolean download1=false;
	private boolean download2=false;
	private boolean download3=false;
	private boolean download4=false;


	private  String ReadString(String sourcePath){

		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(sourcePath));
			String str;
			while((str = br.readLine()) != null) {
				sb.append(str);
				sb.append("\r\n");
			}
			br.close();
		}catch(Exception e){
			setText(e.toString());
		}

		return sb.toString();

	}
	
	@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  base = inflater.inflate(R.layout.download,container,false); //  此处的布局文件是普通的线性布局（此博客忽略）
		Objects.requireNonNull(getDialog()).requestWindowFeature(STYLE_NO_TITLE);
		setCancelable(false);
		mpath1=(TextView)basefindViewById(base,R.id.downloadTextView1);
		mpath2=(TextView)basefindViewById(base,R.id.downloadTextView2);
		mpath3=(TextView)basefindViewById(base,R.id.downloadTextView3);
		mpath4=(TextView)basefindViewById(base,R.id.downloadTextView7);
		mpath_progress1=(ProgressBar)base.findViewById(R.id.downloadProgressBar1);
		mpath_progress2=(ProgressBar)base.findViewById(R.id.downloadProgressBar2);
		mpath_progress3=(ProgressBar)base.findViewById(R.id.downloadProgressBar3);
		mpath_progress4=(ProgressBar)base.findViewById(R.id.downloadProgressBar4);
		TextView overall_progress = (TextView) basefindViewById(base, R.id.downloadTextView4);
		stages_progress=(TextView)basefindViewById(base,R.id.downloadTextView5);
		mlist=(TextView)basefindViewById(base,R.id.downloadTextView6);
		//title=(TextView)basefindViewById(base,R.id.downloadTextView7);
		ImageButton close = (ImageButton) base.findViewById(R.id.downloadImageButton1);
		pause_start1=(ImageButton)base.findViewById(R.id.downloadImageButton2);
		pause_start2=(ImageButton)base.findViewById(R.id.downloadImageButton3);
		pause_start3=(ImageButton)base.findViewById(R.id.downloadImageButton4);
		pause_start4=(ImageButton)base.findViewById(R.id.downloadImageButton5);
		btnCancel = (Button)base.findViewById(R.id.btn_cancel);
//
		close.setVisibility(View.GONE);
		pause_start1.setOnClickListener(onclick);
		pause_start2.setOnClickListener(onclick);
		pause_start3.setOnClickListener(onclick);
		pause_start4.setOnClickListener(onclick);
		btnCancel.setOnClickListener(onclick);
//
		mpath_progress1.setProgress(0);
		mpath_progress2.setProgress(0);
		mpath_progress3.setProgress(0);
		mpath_progress4.setProgress(0);
		mpath1.setText("");
		mpath2.setText("");
		mpath3.setText("");
		mpath4.setText("");

		String version = requireArguments().getString("version");
		String homepath = getArguments().getString("game");
		String address = getArguments().getString("address");
	
		loading_config(version, homepath, address);
		
        return base;
    }
	
	public  void loading_config(String version,String homepath,String address){
		//Open_notice_Service();
		
		mDownloadManager=DownloadManager.getInstance();//必须的

		Version = version;//下载版本--->manifest_version_json
		game_directory = homepath;//主游戏目录->结尾不带"/"
		//
		//一个都不能有误-传入前检测下
		API_Version_client_server_json = address + "/version/";
		API_Assets = address + "/assets/";
		API_Libraries = address + "/maven/";
		String API_Manifest_Version_json = address + "/mc/game/version_manifest.json";

		String assets_root = game_directory + "/assets";
		Version_jar = game_directory+"/versions/"+Version+"/"+ Version+".jar";
		Version_json = game_directory+"/versions/"+Version+"/"+ Version+".json";
		//
		Version_libraries = game_directory+"/libraries/";
		Version_assets = assets_root +"/";

		mhandler.sendEmptyMessage(0);//一切的开端

	}

    @Override
    public void onStart() {
        super.onStart();
	}
	
	@Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // 横屏时取高度为宽度，高度为为 warp_content
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();

        if(screenWidth>screenHeight){
            params.width = (int)(screenHeight*1.1f);
            //params.height = (int)(screenHeight*0.9f);
        }else{
            params.width = (int)(screenWidth*0.8f);
            //params.height = (int)(screenHeight*0.4f);
        }

        getDialog().getWindow().setAttributes(params);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // 横屏时取高度为宽度，高度为为 warp_content
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();

        if(screenWidth>screenHeight){
            params.width = (int)(screenHeight*0.9f);
            //params.height = (int)(screenHeight*0.6f);
        }else{
            params.width = (int)(screenWidth*0.9f);
            //params.height = (int)(screenHeight*0.6f);
        }

        getDialog().getWindow().setAttributes(params);
        super.onResume();

    }

	
	private final View.OnClickListener onclick=new View.OnClickListener(){

		@Override
		public void onClick(View p1)
		{
		 if(p1==pause_start1){
			 //谨慎点击
				download1(mDownloadManager.selfDestruction3());

			}else if(p1==pause_start2){
				//谨慎点击
				download2(mDownloadManager.selfDestruction2());

			}else if(p1==pause_start3){
				//谨慎点击
				download3(mDownloadManager.selfDestruction3());

			}else if(p1==pause_start4){
			 //谨慎点击
			 download4(mDownloadManager.selfDestruction4());

		    }else if (p1 == btnCancel) {
				Objects.requireNonNull(getDialog()).dismiss();
			}
		}
	};

//字体颜色
	private TextView basefindViewById(View base,int id){
		TextView a=base.findViewById(id);
		a.setTextColor(Color.GRAY);
		return a;
	}
	
	@SuppressLint("HandlerLeak")
	private final Handler mhandler=new Handler(){
		@SuppressLint("SetTextI18n")
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case 1000:
					if(Temporary.size()>=1){
						if(!download1){
							mhandler.sendEmptyMessage(101);
							setlibraries();
						}else{
							if(!download2){
								mhandler.sendEmptyMessage(101);
								setlibraries();
							}else{
								if(!download3){
									mhandler.sendEmptyMessage(101);
									setlibraries();
								}else {
									if (!download4) {
										mhandler.sendEmptyMessage(101);
										setlibraries();
									}
								}
							}
						}
					}else{
						setlibraries();
						Message msg1=new Message();
						msg1.what=8;
						msg1.arg1=5;
						mhandler.sendMessage(msg1);
					}
					break;
				case 2000:
					if(Temporary.size()>=1){
						if(!download1){
							mhandler.sendEmptyMessage(101);
							setassets();
						}else{
							if(!download2){
								mhandler.sendEmptyMessage(101);
								setassets();
							}else{
								if(!download3){
									mhandler.sendEmptyMessage(101);
									setassets();
								}else{
									if(!download4){
										mhandler.sendEmptyMessage(101);
										setassets();
									}
								}
							}
						}
					}else{
						setassets();
						Message msg2=new Message();
						msg2.what=8;
						msg2.arg1=6;
						mhandler.sendMessage(msg2);
					}
					break;
				case 0:
					mhandler.sendEmptyMessageAtTime(1, 500);
					break;
				case 1:
					setText("(Download):"+Version+"!");
					Dwonload_Version_json(Version,API_Version_client_server_json+Version+"/json");
					
					break;
				case 2:
					Txt = ReadString(game_directory + "/versions/" + Version + "/" + Version + ".json");
					//String Url=API_Version_client_server_json + Version + "/client";
					Download_client_Assets(Txt);
					break;
				case 3:
					VersionLibraries(Txt);
					VersionAssets(ReadString(Version_assets + "indexes/" + assets_id + ".json"));
					break;
				case 4:
					run_download_libraries();
					break;
				case 101:
					//if(Temporary.get(0)instanceof AssetsUtil){

						object_turn_assets(API_Assets);
						object_turn_libraries();

					//}else if(Temporary.get(0) instanceof LibrariesUtil){

						//object_turn_libraries();
					//}

					break;
				case 5:
					run_download_assets();
					break;
				case 6:

					mpath_progress1.setProgress(100);
					mpath_progress2.setProgress(100);
					mpath_progress3.setProgress(100);
					mpath_progress4.setProgress(100);
					mpath1.setText(getResources().getString(R.string.download_done)+Version);
					mpath2.setText(getResources().getString(R.string.download_done)+Version);
					mpath3.setText(getResources().getString(R.string.download_done)+Version);
					mpath4.setText(getResources().getString(R.string.download_done)+Version);
					append(getResources().getString(R.string.download_done)+Version,100,100);

					
					//-可自定义个回调机制-
					
					break;
				case 8:
					if(!download2&&!download3&&!download1&&!download4){
						mhandler.sendEmptyMessage(3);
					}
					break;
				case 9:
					//网页文件无法下载调用
					/*try
					 {
					     msg.obj;
					 }
					 catch (IOException e)
					 {
					     setText(e.toString());
					 }*/
					break;
				case 10:
					break;
			}
			super.handleMessage(msg);
		}
	};
	private  void object_turn_libraries()
	{
		// 1 0 11 01 1 02 10 32 0 12 0 22 03
		try{
			ArrayList<Object> list=new ArrayList<>();
			if(Temporary.size()>1) {
				LibrariesUtil rock = (LibrariesUtil) Temporary.get(0);
				String url = rock.geturl();
				String path = Path(rock.getpath());
				String name = Path_name(rock.getpath());
				if (!download1) {
					download_manager_1(url, path, name, 1000, name, rock.getsize());
					for (int i = 0; i < Temporary.size(); i++) {
						if (i != 0) {
							list.add((LibrariesUtil) Temporary.get(i));
						}
					}
					Temporary = (ArrayList<Object>) list;
					if (!download2) {
						mhandler.sendEmptyMessage(101);
					} else {
						if (!download3) {
							mhandler.sendEmptyMessage(101);
						} else {
							if (!download4) {
								mhandler.sendEmptyMessage(101);
							}
						}
					}//true未加载
				} else {
					if (!download2) {
						download_manager_2(url, path, name, 1000, name, rock.getsize());
						for (int i = 0; i < Temporary.size(); i++) {
							if (i != 0) {
								list.add((LibrariesUtil) Temporary.get(i));
							}
						}
						Temporary = (ArrayList<Object>) list;
						if (!download3) {
							mhandler.sendEmptyMessage(101);
						} else {
							if (!download1) {
								mhandler.sendEmptyMessage(101);
							} else {
								if (!download4) {
									mhandler.sendEmptyMessage(101);
								}
							}
						}//true未加载
					} else {
						if (!download4) {
							download_manager_4(url, path, name, 1000, name, rock.getsize());
							for (int i = 0; i < Temporary.size(); i++) {
								if (i != 0) {
									list.add((LibrariesUtil) Temporary.get(i));
								}
							}
							Temporary = (ArrayList<Object>) list;
							if (!download2) {
								mhandler.sendEmptyMessage(101);
							} else {
								if (!download3) {
									mhandler.sendEmptyMessage(101);
								} else {
									if (!download1) {
										mhandler.sendEmptyMessage(101);
									}
								}//list2.clear();

							}//true未加载
						} else {
							if (!download3) {
								download_manager_3(url, path, name, 1000, name, rock.getsize());
								for (int i = 0; i < Temporary.size(); i++) {
									if (i != 0) {
										list.add((LibrariesUtil) Temporary.get(i));
									}
								}
								Temporary = (ArrayList<Object>) list;
								if (!download2) {
									mhandler.sendEmptyMessage(101);
								} else {
									if (!download3) {
										mhandler.sendEmptyMessage(101);
									} else {
										if (!download4) {
											mhandler.sendEmptyMessage(101);
										}
									}//list2.clear();

								}//true未加载
							}
						}
					}
				}
			}else{
				LibrariesUtil rock=(LibrariesUtil) Temporary.get(0);
				String url=rock.geturl();
				String path = Path(rock.getpath());
				String name = Path_name(rock.getpath());
				if(!download1){
					download_manager_1(url,path,name,1000,name,rock.getsize());
					Temporary.remove(0);
				}else{
					if(!download2){
						download_manager_2(url,path,name,1000,name,rock.getsize());
						Temporary.remove(0);
					}else{
						if(!download3){
							download_manager_3(url,path,name,1000,name,rock.getsize());
							Temporary.remove(0);
						}else{
							if(!download4){
								download_manager_3(url,path,name,1000,name,rock.getsize());
								Temporary.remove(0);
							}
						}
					}
				}
			}

		}catch(Exception e){
			setText(e.toString());
		}

	}
	private  void object_turn_assets(String Assets)
	{
		//10 11 01 10 21 03 2 0 1 20 22 03
		try{
			ArrayList<Object> list=new ArrayList<>();
			if(Temporary.size()>1){
				AssetsUtil rock=(AssetsUtil) Temporary.get(0);
				String url=Assets+rock.gethash().substring(0,2)+"/"+ rock.gethash();
				String path = Version_assets+"objects/"+rock.gethash().substring(0,2);
				String name = rock.gethash();
				if(!download1){
					download_manager_1(url,path,name,2000,rock.getname(),rock.getsize());
					for (int i = 0; i < Temporary.size(); i++){
						if (i!=0){
							list.add((AssetsUtil)Temporary.get(i));
						}
					}
					Temporary=(ArrayList<Object>)list;
					if(!download2){
						mhandler.sendEmptyMessage(101);
					}else{
						if(!download3){
							mhandler.sendEmptyMessage(101);
						}else{
							if(!download4){
								mhandler.sendEmptyMessage(101);
							}
						}
					}//true未加载
				}else{
					if(!download2){
						download_manager_2(url,path,name,2000,rock.getname(),rock.getsize());
						for (int i = 0; i < Temporary.size(); i++){
							if (i!=0){
								list.add((AssetsUtil)Temporary.get(i));
							}
						}
						Temporary=(ArrayList<Object>)list;
						if(!download3){
							mhandler.sendEmptyMessage(101);
						}else{
							if(!download1){
								mhandler.sendEmptyMessage(101);
							}
						}//true未加载
					}else{
						if(!download3){
							download_manager_3(url,path,name,2000,rock.getname(),rock.getsize());
							for (int i = 0; i < Temporary.size(); i++){
								if (i!=0){
									list.add((AssetsUtil)Temporary.get(i));
								}
							}
							Temporary=(ArrayList<Object>)list;
							if(!download2){
								mhandler.sendEmptyMessage(101);
							}else{
								if(!download3){
									mhandler.sendEmptyMessage(101);
								}else{
									if(!download4){
										mhandler.sendEmptyMessage(101);
									}//list2.clear();

								}//list2.clear();

							}//true未加载
						}else{
							if(!download4){
								download_manager_3(url,path,name,2000,rock.getname(),rock.getsize());
								for (int i = 0; i < Temporary.size(); i++){
									if (i!=0){
										list.add((AssetsUtil)Temporary.get(i));
									}
								}
								Temporary=(ArrayList<Object>)list;
								if(!download2){
									mhandler.sendEmptyMessage(101);
								}else{
									if(!download3){
										mhandler.sendEmptyMessage(101);
									}else{
										if(!download1){
											mhandler.sendEmptyMessage(101);
										}//list2.clear();

									}//list2.clear();

								}//true未加载
							}
						}
					}
				}
			}else if(Temporary.size()==1){
				AssetsUtil rock=(AssetsUtil) Temporary.get(0);
				String url=Assets+rock.gethash().substring(0,2)+"/"+ rock.gethash();
				String path = Version_assets+"objects/"+rock.gethash().substring(0,2);
				String name = rock.gethash();
				if(!download1){
					download_manager_1(url,path,name,2000,rock.getname(),rock.getsize());
					Temporary.remove(0);
				}else{
					if(!download2){
						download_manager_2(url,path,name,2000,rock.getname(),rock.getsize());
						Temporary.remove(0);
					}else{
						if(!download3){
							download_manager_3(url,path,name,2000,rock.getname(),rock.getsize());
							Temporary.remove(0);
						}else{
							if(!download4){
								download_manager_4(url,path,name,2000,rock.getname(),rock.getsize());
								Temporary.remove(0);
							}
						}
					}
				}
			}

		}catch(Exception e){
			setText(e.toString());
		}
	}
	@SuppressLint("SetTextI18n")
	private void Dwonload_Version_json(String id, String url){
		File e=new File(Version_json);
		if(!e.exists()){
			download_manager_1(url,game_directory+"/versions/"+id,Version+".json",2,"Version->"+Version+"->json",0) ;
		}else{
			if(e.isDirectory()){
				//文件夹怎么删除?
				e.delete();
				download_manager_1(url,game_directory+"/versions/"+id,Version+".json",2,"Version->"+Version+"->json",0) ;
			}else{
				mpath1.setText("Version->"+Version+"->json");
				mpath_progress1.setProgress(100);//留空白不好看
				mhandler.sendEmptyMessage(2);
			}
		}
	}
	public void Get_okHttp_Response_body_string(String url1,final int msg1)
	{
		HttpUtil.sendOkHttpRequest(url1, new okhttp3.Callback(){
				@Override
				public void onFailure(@NonNull Call call, @NonNull final IOException e) {
					//网络错误
				}
				@Override
				public void onResponse(@NonNull Call p1, @NonNull Response p2) throws IOException
				{
					final String url_string=p2.body().string();
					new Thread(() -> {
						Message msg = new Message();
						msg.what = msg1;
						msg.obj = url_string;
						//获取网页?
						mhandler.sendMessage(msg);
					}).start();
				}
			});
	}

	@SuppressLint("SetTextI18n")
	private void Download_client_Assets(String json){
		try{
			JSONObject objects=JSON.parseObject(json);
			JSONObject client= objects.getJSONObject("downloads");
			JSONObject main= client.getJSONObject("client");

			String Url=(String) main.get("url");
			int size= (Integer) main.get("size");


			JSONObject assetindex= objects.getJSONObject("assetIndex");
			assets_id=(String)assetindex.get("id");
			String url=((String)assetindex.get("url"));
			File version_assetindex=new File(Version_assets+"indexes/"+(String)assetindex.get("id")+".json");
			File version_client=new File(Version_jar);


			if(!version_client.exists()&&!version_assetindex.exists()){
				download_manager_2(Url,game_directory+"/versions/"+Version,Version+".jar",8,"Version->"+Version+"->jar",size);
				download_manager_3(url,Version_assets+"indexes",(String)assetindex.get("id")+".json",8,"Version->"+assets_id+"->assets->json",0);
				download_manager_4(url,Version_assets+"indexes",(String)assetindex.get("id")+".json",8,"Version->"+assets_id+"->assets->json",0);
			}else if(!version_client.exists()&&version_assetindex.exists()){
				download_manager_2(Url,game_directory+"/versions/"+Version,Version+".jar",8,"Version->"+Version+"->jar",size);
				//同上
				mpath3.setText("Version->"+assets_id+"->assets->json");
				mpath_progress3.setProgress(100);
				mpath4.setText("Version->"+assets_id+"->assets->json");
				mpath_progress4.setProgress(100);
			}else if(version_client.exists()&&!version_assetindex.exists()){
				download_manager_3(url,Version_assets+"indexes",(String)assetindex.get("id")+".json",8,"Version->"+assets_id+"->assets->json",0);
				//同上
				mpath4.setText("Version->"+Version+"->jar");
				mpath_progress4.setProgress(100);
				mpath2.setText("Version->"+Version+"->jar");
				mpath_progress2.setProgress(100);
			}else if(version_client.exists()&&!version_assetindex.exists()){
				download_manager_4(url,Version_assets+"indexes",(String)assetindex.get("id")+".json",8,"Version->"+assets_id+"->assets->json",0);
				//同上
				mpath3.setText("Version->"+Version+"->jar");
				mpath_progress3.setProgress(100);
				mpath2.setText("Version->"+Version+"->jar");
				mpath_progress2.setProgress(100);
			}else if(version_client.exists()&&version_assetindex.exists()){
				if(version_client.isDirectory()){
					version_client.delete();
					//文件夹如何删除?
					setText("Client error");
					download_manager_2(Url,game_directory+"/versions/"+Version,Version+".jar",8,"Version->"+Version+"->jar",size);
				}else{
					if(version_client.length()!=(int)main.get("size")){
						
						version_client.delete();
						setText("Client error");
						download_manager_2(Url,game_directory+"/versions/"+Version,Version+".jar",8,"Version->"+Version+"->jar",size);
					}else{
						
						mpath2.setText("Version->"+Version+"->jar");
						mpath_progress2.setProgress(100);
						mpath3.setText("Version->"+assets_id+"->assets->json");
						mpath_progress3.setProgress(100);
						mpath4.setText("Version->"+assets_id+"->assets->json");
						mpath_progress4.setProgress(100);
						Message msg=new Message();
						msg.what=8;
						msg.arg1=3;
						mhandler.sendMessage(msg);
					}
				}
			}
		}catch(Exception e){
			setText(e.toString());
			
			//此情况是解析发生错误一般null
		}

	} 
	//https://bmclapi2.bangbang93.com/version/1.7.10/server client json 
	private String Turn_Url(String api,String a){
		String b=a.substring(0, a.lastIndexOf(":"));
		String c=a.substring(a.lastIndexOf(":")+1);
		String d=b.substring(b.lastIndexOf(":")+1)+"-"+c+".jar";
		String e=b.substring(0, a.lastIndexOf(":"));
		String f=e.replace(".","/");
		String g=f.replace(":","/");
		return api+g+"/"+c+"/"+d;
	}
	private String Turn_Path(String api,String a){
		//此为拼接
		String b=a.substring(0, a.lastIndexOf(":"));
		String c=a.substring(a.lastIndexOf(":")+1);
		String d=b.substring(b.lastIndexOf(":")+1)+"-"+c+".jar";
		String e=b.substring(0, a.lastIndexOf(":"));
		String f=e.replace(".","/");
		String g=f.replace(":","/");
		return api+g+"/"+c+"/"+d;
	}
	
	
	
	
	//这就是解析version获取libraries数据
	private void VersionLibraries(String json){
		if(json==null){
			mhandler.sendEmptyMessage(3);
			//此为无解析数据-重新下载
		}else{
			JSONObject version_json = JSON.parseObject(json);
			JSONArray version_libraries = version_json.getJSONArray("libraries");
			libraries= new ArrayList<>();
			for (int i = 0 ; i < version_libraries.size();i++){
				JSONObject key = (JSONObject)version_libraries.get(i);
				JSONObject downloads = (JSONObject)key.get("downloads");
				JSONObject artifact=(JSONObject) Objects.requireNonNull(downloads).get("artifact");
				if(artifact!=null){
					LibrariesUtil util =new LibrariesUtil();
					util.setname((String)key.get("name"));
					util.setpath((String)artifact.get("path"));
					util.seturl((String)artifact.get("url"));
					util.setsize((int)artifact.get("size"));
					util.set(true);
					libraries.add(util);
				}//针对不存在srtifact-不处理

			}
			mhandler.sendEmptyMessage(4);
		}
	}
	//同上
	public void VersionAssets(String json){
		if(json==null){
			mhandler.sendEmptyMessage(2);
			return;
		}
		try {
			//String json= ReadString("/sdcard/assets.json");
			JSONObject assetindex=JSON.parseObject(json);
			JSONObject objects= assetindex.getJSONObject("objects");
			String turn_txt=objects.toJSONString();
			Gson gson = new Gson();	
			Map<String, VersionAssetsUtil> map = gson.fromJson(turn_txt,new TypeToken<Map<String,VersionAssetsUtil>>() {}.getType());    
			assets= new ArrayList<>();
			for (Map.Entry<String, VersionAssetsUtil> entry : map.entrySet()) {    
				//System.out.println("key= " +  + " and value= " + );    
				AssetsUtil util=new AssetsUtil();
				util.setname(entry.getKey());
				util.sethash(entry.getValue().gethash());
				util.setsize(entry.getValue().getsize());
				util.set(true);
				assets.add(util);
			}
		} catch (Exception e) {
			//e.toString();
			//建议重载
			setText(e.toString());
		}
	} 
	private String Path_name(String path){
		return path.substring(path.lastIndexOf("/")+1); 
	}
	private String Path(String path){
		return path.substring(0, path.lastIndexOf("/"));
	}
	

	private void append(String name,int a,int b){
		Message msg = new Message(); 

		msg.what =10; 
		msg.obj=name; 
		msg.arg1=a;
		msg.arg2=b;
		mhandler.sendMessage(msg);
		setText(name);

	}
	private  void setText(String a){
		mlist.append(a+"\n");
	}
	
	
	//尺寸
	@SuppressLint("DefaultLocale")
	private String turn_Length(long len){
		if(len<1024){
			return len+"B";
		}
		if(len<1024*1024){
			return String.format("%.2f%s",(len/1024.0),"K");
		}
		if(len<1024*1024*1024)
			return String.format("%.2f%s",(len/(1024*1024.0)),"M");
		return String.format("%.2f%s",(len/(1024*1024*1024.0)),"G");
	}

	private void run_download_libraries(){
		success_libraries=0;
		overall_libraries=0;
		Temporary= new ArrayList<>();
		ArrayList<Object> unexists_File_Manager= new ArrayList<>();
		for(LibrariesUtil util:libraries){
			if(util.get()){
				File file=new File(Version_libraries+util.getpath());
				util.setpath(file.getAbsolutePath());

				util.seturl(Turn_Url(API_Libraries,util.getname()));

				if(file.exists()){
					if(file.isDirectory()){
						file.delete();
						unexists_File_Manager.add(util);
						overall_libraries+=util.getsize();
					}else{
						if(file.length()!=util.getsize()){
							file.delete();
							unexists_File_Manager.add(util);
							overall_libraries+=util.getsize();
						}
					}
				}else{
					unexists_File_Manager.add(util);
					overall_libraries+=util.getsize();
				}
			}else{
				File file=new File(Turn_Path(Version_libraries,util.getname()));
				util.setpath(file.getAbsolutePath());

			}
		}
		if(unexists_File_Manager.size()>=1){
			libraries_loader_size=unexists_File_Manager.size();

			Temporary=unexists_File_Manager;
			mhandler.sendEmptyMessageDelayed(1000,800);
		}else{
			mhandler.sendEmptyMessage(5);
		}
	}
	private void download_status1(boolean status){
		if(status){
			pause_start1.setBackgroundResource(R.drawable.download_pause);
		}else{
			pause_start1.setBackgroundResource(R.drawable.download_play);
		}

	}private void download_status2(boolean status){
		if(status){
			pause_start2.setBackgroundResource(R.drawable.download_pause);
		}else{
			pause_start2.setBackgroundResource(R.drawable.download_play);
		}

	}private void download_status3(boolean status){
		if(status){
			pause_start3.setBackgroundResource(R.drawable.download_pause);
		}else{
			pause_start3.setBackgroundResource(R.drawable.download_play);
		}

	}private void download_status4(boolean status){
	if(status){
		pause_start4.setBackgroundResource(R.drawable.download_pause);
	}else{
		pause_start4.setBackgroundResource(R.drawable.download_play);
	}

}


	private void run_download_assets(){
		success_libraries=0;
		overall_libraries=0;
		Temporary= new ArrayList<>();
		ArrayList<Object> unexists_File_Manager= new ArrayList<>();
		for(AssetsUtil util:assets){
			if(util.get()){
				File file=new File(Version_assets+"objects/"+util.gethash().substring(0,2)+"/"+util.gethash());
				if(file.exists()){
					if(file.isDirectory()){
						file.delete();
						unexists_File_Manager.add(util);
						overall_libraries+=util.getsize();
					}else{
						if(file.length()!=util.getsize()){
							file.delete();
							unexists_File_Manager.add(util);
							overall_libraries+=util.getsize();
						}
					}
				}else{
					unexists_File_Manager.add(util);
					overall_libraries+=util.getsize();
				}
			}
		}
		if(unexists_File_Manager.size()>=1){
			assets_loader_size=unexists_File_Manager.size();
			Temporary=unexists_File_Manager;
			mhandler.sendEmptyMessageDelayed(2000,800);
		}else{
			mhandler.sendEmptyMessage(6);
		}
	}


	private void download1(FilePoint f){

		if(null!=f){
			download_manager_1(
				f.getUrl(),//链接
				f.getFilePath(),//路径
				f.getFileName(),//文件名
				f.getA(),
				f.getObject(),
				f.getSize());

			//完全移植
			setText("重新下载>"+f.getObject());
			//选择继续or选择新下载

		}

	}
	private void download2(FilePoint f){
		if(null!=f){
			download_manager_2(
				f.getUrl(),//链接
				f.getFilePath(),//路径
				f.getFileName(),//文件名
				f.getA(),
				f.getObject(),
				f.getSize());

			//完全移植
			setText("重新下载>"+f.getObject());
			//选择继续or选择新下载
		}


	}
	private void download3(FilePoint f){
		if(null!=f){
			download_manager_3(
				f.getUrl(),//链接
				f.getFilePath(),//路径
				f.getFileName(),//文件名
				f.getA(),
				f.getObject(),
				f.getSize());

			//完全移植
			setText("重新下载>"+f.getObject());
			//选择继续or选择新下载
		}


	}

	private void download4(FilePoint f){
		if(null!=f){
			download_manager_4(
					f.getUrl(),//链接
					f.getFilePath(),//路径
					f.getFileName(),//文件名
					f.getA(),
					f.getObject(),
					f.getSize());

			//完全移植
			setText("重新下载>"+f.getObject());
			//选择继续or选择新下载
		}


	}
	private void download_manager_1(//第一个通道
		final String url,//链接
		final String path,//路径
		final String name,//文件名
		final int a,
		final Object object,
		final int size
	) {
        mDownloadManager.add1(url, path, name,a,object, size,new DownloadListener() {


				@Override
				public void onFinished() {
					download1=false;
					if(a==2000){
						assets_loading_size+=1;
						append((String)object,assets_loading_size,assets_loader_size);
					}else if(a==1000){
						libraries_loading_size+=1;

						append((String)object,libraries_loading_size,libraries_loader_size);
					}

					success_libraries+=size;//共用-针对下载成功的不起作用
					mhandler.sendEmptyMessage(a);

				}
				@SuppressLint("SetTextI18n")
				@Override
				public void onProgress(float progress, long i, long s)
				{
					mpath_progress1.setProgress((int) (progress * 100));
					mpath1.setText(object+"："+turn_Length(i) + File.pathSeparatorChar+turn_Length(s));

				}
				@Override
				public void onPause() {//暂停
					setText(object+"Pause");

				}
				@Override
				public void onCancel() {//取消
					setText(object+"Cancel");

				}
			});
		download1=true;
		download_status1(mDownloadManager.download1_stop_start());

    }
	private  void download_manager_2(//第一个通道
		final String url,//链接
		final String path,//路径
		final String name,//文件名
		final int a,
		final Object object,
		final int size

	) {
        mDownloadManager.add2(url, path, name, a, object,size ,new DownloadListener() {



				@Override
				public void onFinished() {
					download2=false;
					if(a==2000){
						assets_loading_size+=1;

						append((String)object,assets_loading_size,assets_loader_size);
					}else if(a==1000){
						libraries_loading_size+=1;
						append((String)object,libraries_loading_size,libraries_loader_size);
					}
					success_libraries+=size;

					mhandler.sendEmptyMessage(a);

				}
				@Override
				public void onProgress(float progress, long i, long s)
				{
					mpath_progress2.setProgress((int) (progress * 100));
					mpath2.setText(object+"："+turn_Length(i) + File.pathSeparatorChar+turn_Length(s));

				}
				@Override
				public void onPause() {//暂停
					setText(object+"Pause");
				}
				@Override
				public void onCancel() {//取消
					setText(object+"Cancel");
				}
			});
		download2=true;
		download_status2(mDownloadManager.download2_stop_start());


    }
	private  void download_manager_3(//第一个通道
		final String url,//链接
		final String path,//路径
		final String name,//文件名
		final int a,
		final Object object,
		final int size
	) {
        mDownloadManager.add3(url, path, name,a,object,size, new DownloadListener() {



				@Override
				public void onFinished() {
					download3=false;
					if(a==2000){
						assets_loading_size+=1;

						append((String)object,assets_loading_size,assets_loader_size);
					}else if(a==1000){
						libraries_loading_size+=1;
						append((String)object,libraries_loading_size,libraries_loader_size);
					}
					success_libraries+=size;

					mhandler.sendEmptyMessage(a);
				}
				@SuppressLint("SetTextI18n")
				@Override
				public void onProgress(float progress, long i, long s)
				{
					mpath_progress3.setProgress((int) (progress * 100));
					mpath3.setText(object+"："+turn_Length(i) + File.pathSeparatorChar+turn_Length(s));

				}
				@Override
				public void onPause() {//暂停
					setText(object+"Pause");
				}
				@Override
				public void onCancel() {//取消
					setText(object+"Cancel");
				}
			});
		download3=true;
		download_status3(mDownloadManager.download3_stop_start());

    }
	private  void download_manager_4(//第一个通道
									 final String url,//链接
									 final String path,//路径
									 final String name,//文件名
									 final int a,
									 final Object object,
									 final int size
	) {
		mDownloadManager.add4(url, path, name,a,object,size, new DownloadListener() {



			@Override
			public void onFinished() {
				download4=false;
				if(a==2000){
					assets_loading_size+=1;

					append((String)object,assets_loading_size,assets_loader_size);
				}else if(a==1000){
					libraries_loading_size+=1;
					append((String)object,libraries_loading_size,libraries_loader_size);
				}
				success_libraries+=size;

				mhandler.sendEmptyMessage(a);
			}
			@SuppressLint("SetTextI18n")
			@Override
			public void onProgress(float progress, long i, long s)
			{
				mpath_progress4.setProgress((int) (progress * 100));
				mpath4.setText(object+"："+turn_Length(i) + File.pathSeparatorChar+turn_Length(s));

			}
			@Override
			public void onPause() {//暂停
				setText(object+"Pause");
			}
			@Override
			public void onCancel() {//取消
				setText(object+"Cancel");
			}
		});
		download4=true;
		download_status4(mDownloadManager.download4_stop_start());

	}
	@SuppressLint("SetTextI18n")
	private void setlibraries(){
		//overall_progress.setText(getResources().getString(R.string.total)+libraries_loading_size+"/"+libraries_loader_size);
		stages_progress.setText(turn_Length(success_libraries)+File.pathSeparatorChar+turn_Length(overall_libraries));
	}
	@SuppressLint("SetTextI18n")
	private void setassets(){
		//overall_progress.setText(getResources().getString(R.string.total)+assets_loading_size+"/"+assets_loader_size);
		stages_progress.setText(turn_Length(success_libraries)+File.pathSeparatorChar+turn_Length(overall_libraries));

	}
	private long overall_libraries;
	private long success_libraries;//同上-assets共用-迷惑行为
//不太需要?
}

