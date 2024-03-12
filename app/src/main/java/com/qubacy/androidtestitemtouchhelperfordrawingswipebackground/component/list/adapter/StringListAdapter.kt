package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.adapter

import android.view.ViewGroup
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.adapter.ChoosableListAdapter
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.ChoosableItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.item.content.StringContentItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.item.content.data.StringContentItemData

class StringListAdapter(

) : ChoosableListAdapter<StringContentItemView, StringContentItemData>() {
    companion object {
        const val TAG = "StringListAdapter"
    }

    class StringListItemViewHolder(
        itemView: ChoosableItemView<StringContentItemView, StringContentItemData>
    ) : ChoosableListItemViewHolder<StringContentItemView, StringContentItemData>(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringListItemViewHolder {
        val contentItemView = StringContentItemView(parent.context, null)
        val itemView = ChoosableItemView(parent.context, null, contentItemView)

        return StringListItemViewHolder(itemView)
    }
}



//class StringListAdapter(
//
//) : RecyclerView.Adapter<StringListAdapter.StringListItemViewHolder>() {
//    companion object {
//        const val TAG = "StringListAdapter"
//    }
//
//    class StringListItemViewHolder(
//        val itemBinding: ComponentListItemBinding
//    ) : RecyclerView.ViewHolder(itemBinding.root) {
//        companion object {
//            const val DEFAULT_HINT_GUIDELINE_POSITION = 0.5f
//        }
//
//        private var mLeftHintGuidelinePosition: Float = DEFAULT_HINT_GUIDELINE_POSITION
//        private var mRightHintGuidelinePosition: Float = DEFAULT_HINT_GUIDELINE_POSITION
//
//        init {
//            initValues()
//            initLayout()
//        }
//
//        private fun initValues() {
//            val resources = itemBinding.root.context.resources
//            val leftHintGuidelinePosition =
//                ResourcesCompat.getFloat(resources,
//                    R.dimen.component_list_item_left_hint_guideline_position
//                )
//
//            mLeftHintGuidelinePosition = leftHintGuidelinePosition
//            mRightHintGuidelinePosition = 1 - leftHintGuidelinePosition
//        }
//
//        private fun initLayout() {
//            itemBinding.componentListItemGuidelineHorizontalHintLeft
//                .setGuidelinePercent(mLeftHintGuidelinePosition)
//            itemBinding.componentListItemGuidelineHorizontalHintRight
//                .setGuidelinePercent(mRightHintGuidelinePosition)
//        }
//
//        fun setData(text: String) {
//            itemBinding.componentListItemText.text = text
//        }
//
//        fun resetView() {
//            itemBinding.componentListItemContent.translationX = 0f
//
//            itemBinding.componentListItemBackgroundHintLeft.apply {
//                if (isInitialized()) resetAnimation()
//            }
//            itemBinding.componentListItemBackgroundHintRight.apply {
//                if (isInitialized()) resetAnimation()
//            }
//        }
//    }
//
//    private val mItems: MutableList<String> = mutableListOf()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringListItemViewHolder {
//        val viewHolderBinding = ComponentListItemBinding.inflate(
//            LayoutInflater.from(parent.context), parent, false
//        ).apply {
//
//        }
//
//        return StringListItemViewHolder(viewHolderBinding)
//    }
//
//    override fun onBindViewHolder(holder: StringListItemViewHolder, position: Int) {
//        val item = mItems[position]
//
//        holder.setData(item)
//    }
//
//    override fun getItemCount(): Int {
//        return mItems.size
//    }
//
//    fun removeItemAtPosition(position: Int) {
//        mItems.removeAt(position)
//
//        notifyItemRemoved(position)
//    }
//
//    fun addItem(item: String) {
//        mItems.add(item)
//
//        notifyItemInserted(mItems.size - 1)
//    }
//
//    fun setItems(items: List<String>) {
//        mItems.apply {
//            clear()
//            addAll(items)
//        }
//
//        notifyDataSetChanged()
//    }
//}