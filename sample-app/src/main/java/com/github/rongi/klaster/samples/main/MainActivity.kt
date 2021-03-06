package com.github.rongi.klaster.samples.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.launch
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.examples.customviewholder.CustomViewHolderExampleActivity
import com.github.rongi.klaster.samples.examples.functional.FunctionalExampleActivity
import com.github.rongi.klaster.samples.examples.multipleviewtypes.MultipleViewTypesExampleActivity
import com.github.rongi.klaster.samples.examples.simple.SimpleExampleActivity
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: RecyclerView.Adapter<*>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    val items = listItems().toMutableList()

    adapter = Klaster.get()
      .itemCount(items.size)
      .view(R.layout.list_item, layoutInflater)
      .bind { position ->
        val item = items[position]
        item_text.text = item.name
        itemView.onClick = { onListItemClick(item.id) }
      }
      .build()

    recycler_view.adapter = adapter

    adapter.notifyDataSetChanged()
  }

  private fun onListItemClick(id: String) = when (id) {
    "simple" -> SimpleExampleActivity::class.launch(this)
    "multiple item types" -> MultipleViewTypesExampleActivity::class.launch(this)
    "view holder" -> CustomViewHolderExampleActivity::class.launch(this)
    "functional" -> FunctionalExampleActivity::class.launch(this)
    else -> throw IllegalStateException("Unknown id: $id")
  }

  private fun listItems(): List<ExampleListItem> {
    return listOf(
      ExampleListItem(
        id = "simple",
        name = "Simple Example"
      ),
      ExampleListItem(
        id = "multiple item types",
        name = "Multiple Item Types"
      ),
      ExampleListItem(
        id = "view holder",
        name = "Custom ViewHolder"
      ),
      ExampleListItem(
        id = "functional",
        name = "Functional Example"
      )
    )
  }

}

class ExampleListItem(
  val id: String,
  val name: String
)