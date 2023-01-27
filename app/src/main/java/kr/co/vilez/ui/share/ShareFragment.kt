package kr.co.vilez.ui.share

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.vilez.R
import kr.co.vilez.databinding.FragmentShareBinding
import kr.co.vilez.ui.MainActivity
import kr.co.vilez.util.ApplicationClass
import retrofit2.awaitResponse


private const val TAG = "공유_ShareFragment"
class ShareFragment : Fragment() {
    private lateinit var binding: FragmentShareBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var shareAdapter: FragmentShareAdapter

    private lateinit var shareDatas: ArrayList<ShareData>
    private var index = 0

    private lateinit var mContext: Context

    val items = arrayListOf<String>(
        "화장품/미용",
        "생활/건강",
        "식품",
        "스포츠/레저",
        "가구/인테리어",
        "디지털/가전",
        "출산/육아",
        "패션잡화",
        "여가/생활편의",
        "패션의류",
        "도서")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // context가 필요할때는 이거 갖다쓰면 됨
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // findViewById 할 필요없이 binding.버튼Id, binding.텍스트뷰Id 로 바로 접근해서 사용 가능!
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_share, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // 뷰 생성 후 초기화
        initView()
    }

    fun initView() {
        // 데이터 가져오기
        shareDatas = arrayListOf()

        // 어댑터 생성
        shareAdapter = FragmentShareAdapter(shareDatas)
        shareAdapter.setItemClickListener(object : FragmentShareAdapter.OnItemClickListener {
            // listview item 클릭시 실행할 메소드
            override fun onClick(view: View, position: Int) {

                Log.d(TAG, "onClick: ${shareDatas[position].tv_name} clicked!")
            }
        })

        // 리사이클러뷰에 어댑터 등록
        binding.rv.apply {
            adapter = shareAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }

        var num = 0;
        var max = 10;
        CoroutineScope(Dispatchers.Main).launch {
            val result =
                ApplicationClass.retrofitShareService.boardList(num++, max).awaitResponse().body();

            if(result?.flag == "success") {
                for(data in result.data){
                    var shareData:ShareData
                    if(data.shareListDto.list.size == 0){
                        shareData = ShareData(
                            "https://kr.object.ncloudstorage.com/vilez/basicProfile.png",
                            data.shareListDto.title + " "
                        );
                    } else {
                        shareData = ShareData(
                            data.shareListDto.list[0].path,
                            data.shareListDto.title + " "
                        );
                    }
                        shareDatas.add(shareData)
//                    Log.d(TAG, "initView: ${data.shareListDto.nickName}");
//                    Log.d(TAG, "initView: ${data.shareListDto.list[0].path}");
                }
            }
            shareAdapter.notifyItemInserted(index-1)
        }

        val items = resources.getStringArray(R.array.my_array)
        //val myAdapter = ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item,items)
        val arrayAdapter = ArrayAdapter<String>(
            mContext,
            android.R.layout.simple_spinner_dropdown_item,
            items
        )


        binding.spinner.adapter = arrayAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                binding.textView.setText(items.get(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.textView.setText("선택")
            }
        }

        binding.rv.setOnScrollChangeListener{ v, scollX, scrollY,
                                                  oldScrollX, oldScrollY ->
//            Log.d(TAG,"스크롤 감지");

            if(!v.canScrollVertically(1)){
                CoroutineScope(Dispatchers.Main).launch {
                    val result =
                        ApplicationClass.retrofitShareService.boardList(num++, max).awaitResponse()
                            .body();
                    Log.d(TAG, "initView: ${result?.data}")
                    if (result?.data?.size != 0) {
                        if (result?.flag == "success") {
                            for (data in result.data) {
                                var shareData:ShareData
                                if(data.shareListDto.list.size == 0){
                                    shareData = ShareData(
                                        "https://kr.object.ncloudstorage.com/vilez/basicProfile.png",
                                        data.shareListDto.title + " "
                                    );
                                } else {
                                    shareData = ShareData(
                                        data.shareListDto.list[0].path,
                                        data.shareListDto.title + " "
                                    );
                                }
                                shareDatas.add(shareData)
//                            Log.d(TAG, "initView: ${data.shareListDto.nickName}");
//                    Log.d(TAG, "initView: ${data.shareListDto.list[0].path}");
                            }
                        }
                        shareAdapter.notifyItemInserted(index - 1)
                    }
                }
            }

        }

//        binding.btnAdd.setOnClickListener { // 방법1 : 버튼에 직접 클릭이벤트 리스너 달기 (이거 쓰려면 xml에 onClick 지워야함)
//            Log.d(TAG, "initView: 클릭 ")
//            val shareData = ShareData(R.drawable.img_default_profile, "test${index++}")
//            shareDatas.add(shareData)
//            shareAdapter.notifyItemInserted(index-1) // 이게 아래코드보다 오버헤드 더 적음
//            // shareAdapter.notifyDataSetChanged()
//        }

    }

//
    fun addItem(view:View) { // 방법 2 : xml의 버튼에 onClick속성으로 addItem 메소드 넣기
        // 클릭 리스너 달아줘도 되고, xml에 선언해놓은 이 메소드에서 버튼 클릭 이벤트 동작 됨
        Log.d(TAG, "addItem: 버튼 클릭")
        val shareData = ShareData("", "test${index++}")
        shareDatas.add(shareData)
        shareAdapter.notifyItemInserted(index-1) // 이게 아래코드보다 오버헤드 더 적음
    }
}