package csx.haha.com.optimization.s1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.util.Arrays;
import java.util.Random;

import csx.haha.com.optimization.R;


public class S1MemoryChurnActivity extends Activity {
    public static final String LOG_TAG = "Ricky";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1_caching_exercise);

        Button theButtonThatDoesFibonacciStuff = (Button) findViewById(R.id.caching_do_fib_stuff);
        theButtonThatDoesFibonacciStuff.setText("内存抖动");

        theButtonThatDoesFibonacciStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imPrettySureSortingIsFree();
            }
        });

        Button bt_s1_big_compute = (Button) findViewById(R.id.bt_s1_big_compute);
        bt_s1_big_compute.setText("大量计算");

        bt_s1_big_compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, String.valueOf(computeFibonacci(36)));
            }
        });

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");


        Button theButtonThatDoesFibonacciStuff2 = (Button) findViewById(R.id.caching_do_fib_stuff2);
        theButtonThatDoesFibonacciStuff2.setText("内存优化后");

        theButtonThatDoesFibonacciStuff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imPrettySureSortingIsFree2();
            }
        });
        Button bt_s1_optimization_compute = (Button) findViewById(R.id.bt_s1_optimization_compute);
        bt_s1_optimization_compute.setText("计算优化后");

        bt_s1_optimization_compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, String.valueOf(computeFibonacciNoRecuresion(36)));
            }
        });
        WebView webView2 = (WebView) findViewById(R.id.webview2);
        webView2.getSettings().setUseWideViewPort(true);
        webView2.getSettings().setLoadWithOverviewMode(true);
        webView2.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

    /**
     *　排序后打印二维数组，一行行打印
     */
    public void imPrettySureSortingIsFree() {
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];
        Random randomGenerator = new Random();
        for(int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

        for(int i = 0; i < lotsOfInts.length; i++) {
            String rowAsStr = "";
            //排序
            int[] sorted = getSorted(lotsOfInts[i]);
            //拼接打印
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                rowAsStr += sorted[j];
                if(j < (lotsOfInts[i].length - 1)){
                    rowAsStr += ", ";
                }
            }
            Log.i("ricky", "Row " + i + ": " + rowAsStr);
        }


    }
    /**
     *　排序后打印二维数组，一行行打印
     */
    public void imPrettySureSortingIsFree2() {
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];
        Random randomGenerator = new Random();
        for(int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

        //优化以后
        StringBuilder sb = new StringBuilder();
        String rowAsStr = "";
        for(int i = 0; i < lotsOfInts.length; i++) {
            //清除上一行
            sb.delete(0,rowAsStr.length());
            //排序
            int[] sorted = getSorted(lotsOfInts[i]);
            //拼接打印
            for (int j = 0; j < lotsOfInts[i].length; j++) {
//                rowAsStr += sorted[j];
                sb.append(sorted[j]);
                if(j < (lotsOfInts[i].length - 1)){
//                    rowAsStr += ", ";
                    sb.append(", ");
                }
            }
            rowAsStr = sb.toString();
            Log.i("ricky", "Row " + i + ": " + rowAsStr);
        }


    }
    public int computeFibonacci(int positionInFibSequence) {
        //0 1 1 2 3 5 8
        if (positionInFibSequence <= 2) {
            return 1;
        } else {
            return computeFibonacci(positionInFibSequence - 1)
                    + computeFibonacci(positionInFibSequence - 2);
        }
    }

    public int computeFibonacciNoRecuresion(int positionInFibSequence) {
        int pre = 0;
        int current = 1;
        int result = 1;
        for (int i = 1; i < positionInFibSequence; ++i) {
            result = pre + current;
            pre = current;
            current = result;
        }
        return result;
    }


    public int[] getSorted(int[] input){
        int[] clone = input.clone();
        Arrays.sort(clone);
        return clone;
    }

}
