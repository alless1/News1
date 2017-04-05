package alless.demovolley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image);
//        volleyGetWithStringRequest();
//        volleyGetWithJsonObjectRequest();
//        volleyPostWithStringRequest();
//        volleyPostWithJsonObjectRequest();
//        loadImageByVolley();
        loadImageWithCache();
    }


    /**
     *  通过ImageLoader加载及缓存网络图片
     *  new ImageLoader(RequestQueue queue,ImageCache imageCache)
     *  queue：请求队列
     *  imageCache：一个用于图片缓存的接口，一般需要传入它的实现类
     *
     *  getImageListener(ImageView view, int defaultImageResId, int errorImageResId)
     *  view：ImageView对象
     *  defaultImageResId：默认的图片的资源Id
     *  errorImageResId：网络图片加载失败时显示的图片的资源Id
     */
    private void loadImageWithCache() {
        String url = "http://10.0.2.2:8080/c.jpg";
        ImageLoader loader = new ImageLoader(MyApplication.getQueue(), new BitmapCache());
        ImageLoader.ImageListener listener =loader.getImageListener(mImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        loader.get(url,listener);
    }





    /**
     *  通过Volley加载网络图片
     *
     *  new ImageRequest(String url,Listener listener,int maxWidth,int maxHeight,Config decodeConfig,ErrorListener errorListener)
     *  url：请求地址
     *  listener：请求成功后的回调
     *  maxWidth、maxHeight：设置图片的最大宽高，如果均设为0则表示按原尺寸显示
     *  decodeConfig：图片像素的储存方式。Config.RGB_565表示每个像素占2个字节，Config.ARGB_8888表示每个像素占4个字节等。
     *  errorListener：请求失败的回调
     */
 /*   private void loadImageByVolley() {
        String url = "http://10.0.2.2:8080/a.jpg";
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mImageView.setImageBitmap(response);
                Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_SHORT).show();

            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
        MyApplication.getQueue().add(request);
    }*/

    /**
     * JsonObjectRequest演示 Post
     */
/*    private void volleyPostWithJsonObjectRequest() {
        String url = "http://10.0.2.2:8080/categories.json";
        Map<String, String> map = new HashMap<>();
        map.put("user","zhangsan");//未配置服务器,结果失败
        JSONObject jsonObject = new JSONObject(map);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "请求成功:" + response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "请求失败:" , Toast.LENGTH_SHORT).show();

            }
        });


        MyApplication.getQueue().add(request);

    }*/

    /**
     * StringRequest演示 POST
     */
/*    private void volleyPostWithStringRequest() {
        String url = "http://10.0.2.2:8080/categories.json";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "请求成功:" + response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "请求失败:" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user","zhangsan");//未配置服务器,结果失败
                return map;
            }
        };

        MyApplication.getQueue().add(request);

    }*/


    /**
     * JsonObjectRequest演示 GET
     */
/*    private void volleyGetWithJsonObjectRequest() {
        String url = "http://10.0.2.2:8080/categories.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "请求成功:" + response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "请求失败:" , Toast.LENGTH_SHORT).show();

            }
        });
        MyApplication.getQueue().add(request);

    }*/



    /**
     * StringRequest演示 GET
     */
  /*  private void volleyGetWithStringRequest() {
        String url = "http://10.0.2.2:8080/categories.json";
        StringRequest request = new StringRequest(Request.Method.GET,url,mListener,mErrorListener);
        request.setTag("stringGet");//设标记,方便取消
        MyApplication.getQueue().add(request);
    }
    private Response.Listener<String> mListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
            //存在乱码问题,未处理.
                Toast.makeText(MainActivity.this,"请求成功"+ new String(response.getBytes(),"utf-8"),Toast.LENGTH_SHORT).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };
    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this,"请求失败",Toast.LENGTH_SHORT).show();

        }
    };*/
}
