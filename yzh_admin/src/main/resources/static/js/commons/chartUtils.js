/**
 * 饼图的公用方法
 * @param chartObj
 * @param data
 */
function initPie(chartObj,data){
	option = {
			title : {
				text: '',
				//subtext: '纯属虚构',
				x:'left'
			}, 
			color: [
					'#02b3c5','#f07476','#ffb04a','#f0e074','#5ab1ef',
					'#5aefae','#6f7a85','#856f84','#b6a2de','#9c5f60'
				],
			tooltip : {
				trigger: 'item',
				formatter: "{a} <br/>{b} : {c} ({d}%)" 
			},
			legend: {
				x : 'left',
				y : 'bottom',
				data:data.legends
			},
			calculable : true,
			series : [

				{
				    labelLine:{
                    normal:{
                        length:5

                      }
                     },
					name:data.name,
					type:'pie',
					radius : [30, 90],
					center : ['40%', 100],
					data:data.series
				}
			]
		};
	
	chartObj.setOption(option);

}
/**
 * 柱状图的公用方法
 * @param optionId  divID
 * @param dataX		x轴
 * @param dataY     y轴
 * @param gapWidth  间距
 */
function initBars_tab_id_(option,dataX,dataY,gapWidth){
    var boxOfficeOption = {
        //鼠标触发提示数量
        tooltip: {
            trigger: "axis"
        },
        xAxis: {
            type: 'category',
            data: dataX
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: '年龄百分比',
            data: dataY,
            type: 'bar',
            //设置柱的宽度，要是数据太少，柱子太宽不美观~
            barWidth: gapWidth
        }],
        itemStyle: {
            normal: {
                //好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
                color: function (params) {
                    // build a color map as your need.
                    var colorList = [
                        '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                        '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                        '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                    ];
                    return colorList[params.dataIndex]
                },
                //以下为是否显示，显示位置和显示格式的设置了
                label: {
                    show: true,
                    position: 'top',
                    formatter: '{b}\n{c}'
                }
            }
        }
    };
    option.setOption(boxOfficeOption);
}
/**
 * 柱状图的公用方法
 * @param optionId  divID
 * @param dataX		x轴
 * @param dataY     y轴
 * @param gapWidth  间距
 */
function initBarsMultiple(option, dataX, dataY, gapWidth, legend) {
    var boxOfficeOption = {
        title: {
            text: "影片票房统计",
            textStyle: {
                color: "#436EEE",
                fontSize: 17
            }
        },
        //鼠标触发提示数量
        tooltip: {
            trigger: "axis"
        },
        legend: {
            data: legend
        },
        //x轴显示
        xAxis: {
            data: dataX,
            splitLine: {
                show: false
            }
        },
        //y轴显示
        yAxis: {
            splitLine: {
                show: false
            }
        },
        series: [
            {
                name: "政策法规",
                type: "bar",
                data: '',
                barWidth: 38,
                itemStyle: {
                    normal: {color: "#FFFF49"}
                }
            },
            {
                name: "经办规程",
                type: "bar",
                data: '',
                barWidth: 38,
                itemStyle: {
                    normal: {color: "#FF8849"}
                }
            },
            {
                name: "业务场景模拟",
                type: "bar",
                data: '',
                barWidth: 38,
                itemStyle: {
                    normal: {color: "#3FBB49"}
                }
            },
            {
                name: "常见问题",
                type: "bar",
                stack: "业务",
                data: '',//
                barWidth: 38,
                itemStyle: {
                    normal: {color: "#56C4A5"}
                }
            }
        ]
    };
    option.setOption(boxOfficeOption);
}
/**
 *
 * @param initId 初始化divId
 * @param title 头部 ['系统登录','微信登录','APP登录','满天星登录','影核登录'];
 * @param date 日期数组 [1,2,3,4,5,6,7,8];
 * @param data 填充数据 {name: title, type: 'line', stack: '总量', areaStyle: {normal: {}}, data: data }
 */
function initTrend(initId,title,date,data) {
    var trend = echarts.init(document.getElementById(initId));
    var trendOption = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            data: title
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: date
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: data
    }
    trend.setOption(trendOption);
}
/**
 * 地区分布的公用方法
 * @param chartObj
 * @param data
 */
function initArea(chartObj,data){
	optionMap = {
			title: {  
                  
            },  
            tooltip : {  
                trigger: 'item'  
            },  

            dataRange: {  
                orient: 'vertical',  
                min:data.min,  
                max:data.max,  
                x: 'left',  
                y: 'bottom',  
                text: ['高','低'],       
             //   calculable : true  
            },   
            series : [  
                {  
                    name: '人数',  
                    type: 'map',  
                    mapType: 'china',  
                    roam: false,
		            itemStyle:{
		                normal:{label:{show:true}},
                        emphasis: {  
                            borderWidth:1,  
                            borderColor:'#fff',  
                            color: '#ff69b4',  
                            label: {  
                                show: true,  
                                textStyle: {  
                                    color: '#fff'  
                                }  
                            }  
                        } 
		            }, 
                    data:data.series
                }  
            ] 
		}
	chartObj.setOption(optionMap); 
}