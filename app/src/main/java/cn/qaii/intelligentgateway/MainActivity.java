package cn.qaii.intelligentgateway;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.List;

import cn.qaii.intelligentgateway.common.dao.LiveDao;
import cn.qaii.intelligentgateway.common.http.LiveRequest;
import cn.qaii.intelligentgateway.model.LiveInfo;

public class MainActivity extends Activity implements OnClickListener{
	private static final int PAGE_SIZE = 10;
	private int pageIndex = 1;
	private LiveDao mLiveDao = new LiveDao();
	private TextView tvData;

	private Handler mHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LiveRequest.GET_LIVE_LIST_SUCCESSED:
				List<LiveInfo> infos = (List<LiveInfo>)msg.obj;
				mLiveDao.syncData(infos);
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < infos.size(); i++) {
					sb.append(infos.get(i).getTitle()).append("\n");
				}
				tvData.setText(sb);
				break;
			default:
				break;
			}
		}

	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData = (TextView)this.findViewById(R.id.tv_data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_request:
			new LiveRequest(this, mHandler).getLiveInfoList(PAGE_SIZE, pageIndex, 0, "");
			break;

		default:
			break;
		}
		
	}
}
