package com.example.inventory


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.data.Item
import com.example.inventory.data.Measurement
import com.example.inventory.data.Strength
import com.example.inventory.data.User
import com.example.inventory.databinding.FragmentUserBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries
import kotlin.math.pow
import kotlin.math.sqrt


class UserFragment : Fragment() {
    private val navigationArgs: ItemDetailFragmentArgs by navArgs()


//    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels {
        UserViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }
    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater,container,false)

        return binding.root
    }
    private fun getData() {
            viewModel.getData()
    }


    var mediatorLiveData = MediatorLiveData<Triple<List<User>?, List<Measurement>?, List<Strength>?>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
//            getData()

             var listOfInputMeasures : Array<Int> = arrayOf()


            mediatorLiveData.addSource(viewModel.allUsers) { duomenys1 ->
                val duomenys2 = viewModel.allMeasurements.value
                val duomenys3 = viewModel.allStrengths.value
                mediatorLiveData.value = Triple(duomenys1, duomenys2, duomenys3)
            }

            mediatorLiveData.addSource(viewModel.allMeasurements) { duomenys2 ->
                val duomenys1 = viewModel.allUsers.value
                val duomenys3 = viewModel.allStrengths.value
                mediatorLiveData.value = Triple(duomenys1, duomenys2, duomenys3)
            }

            mediatorLiveData.addSource(viewModel.allStrengths) { duomenys3 ->
                val duomenys1 = viewModel.allUsers.value
                val duomenys2 = viewModel.allMeasurements.value
                mediatorLiveData.value = Triple(duomenys1, duomenys2, duomenys3)
            }

            val lineEntries = ArrayList<Entry>()
            val pointEntries = ArrayList<Entry>()

            mediatorLiveData.observe(viewLifecycleOwner, Observer { data ->
                val users = data.first
                val measurements = data.second
                val strengths = data.third
                var minDistance = 999999.99
                var measure = 0

                if (users != null && measurements != null && strengths != null) {
                    for (i in 0 until users.count() - 2 step 3) {
                        var s1 = users[i].stiprumas
                        var s2 = users[i + 1].stiprumas
                        var s3 = users[i + 2].stiprumas
//                        binding.textView7.text = "here"
                        for (j in 0 until strengths.count() - 2 step 3) {
                            var ss1 =
                                (strengths[j].stiprumas.toDouble() - s1).pow(2.0)
                            var ss2 =
                                (strengths[j + 1].stiprumas.toDouble() - s2).pow(
                                    2.0
                                )
                            var ss3 =
                                (strengths[j + 2].stiprumas.toDouble() - s3).pow(
                                    2.0
                                )
                            var distance = sqrt(ss1 + ss2 + ss3)

                            if (minDistance > distance) {
                                minDistance = distance
                                measure = strengths[j].matavimas
                            }
                        }
                        listOfInputMeasures += measure
                        minDistance = 999999.0
                        measure = 0
                    }

                    for (i in 0 until measurements.count()) {
                        pointEntries.add(Entry(
                            measurements[i].x.toFloat(),
                            measurements[i].y.toFloat()
                        ))

                        for (j in 0 until listOfInputMeasures.count()) {
                            if (measurements[i].matavimas == listOfInputMeasures[j]) {
                                lineEntries.add(Entry(
                                    measurements[i].x.toFloat(),
                                    measurements[i].y.toFloat()
                                ))
                            }
                        }
                    }

                    val lineChart = view.findViewById<LineChart>(R.id.linechart)

                    val pointDataSet = LineDataSet(pointEntries, "Matavimai")
                    pointDataSet.color = Color.TRANSPARENT
                    pointDataSet.setCircleColor(Color.BLUE)

                    pointDataSet.setDrawCircles(true)

                    val lineDataSet = LineDataSet(lineEntries, "Iš db")
                    lineDataSet.color = Color.RED
                    lineDataSet.setDrawCircles(true)
                    lineDataSet.setCircleColor(Color.RED)

                    val lineData = LineData(pointDataSet,lineDataSet)

                    lineChart.data = lineData
                    lineChart.invalidate()
                }
            })
        }catch (e : Exception){
            binding.textView7.text = e.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






