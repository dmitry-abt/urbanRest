$(function() {
    // Init map
    var mapboxUrl = 'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}',
    mapboxAccessToken = 'pk.eyJ1IjoiYWxleGFuZGVyMzQzOTYiLCJhIjoiY2p4dXQ5d3FjMDQ5bDNubW5wamRzMnEyZyJ9.upZaF0zYwJ8NK-9Wub4MOg',
    mapboxMapId = 'mapbox.streets';

    var isNeedNeighborGraph = false;
    var isNeedPedestrianGraph = false;
    var isNeedPedestrianConcaveHull = false;
    var isNeedCarGraph = false;
    var isNeedCarConcaveHull = false;
    var graphRadius = 500;
    var drawControl;

    var tileLayer = new L.tileLayer(mapboxUrl, {
        minZoom: 9,
        maxZoom: 18,
        id: mapboxMapId,
        accessToken: mapboxAccessToken
    });

    var map = new L.map('map', {
        layers: [tileLayer],
        attributionControl: false
    });

    // Fit the map to a bounding box
    var southWest = L.latLng(59.72, 29.422),
    northEast = L.latLng(60.122, 31.188),
    bounds = L.latLngBounds(southWest, northEast);

    map.setMaxBounds(bounds);
    map.fitBounds(bounds);

    // Init main data structure
    featureGroupNames = {
        "Block": "#F0E68C",
        "Road": "#FFAE73",
        "PedestrianRoad": "#FF9A27",
        "CarRoad": "#372913",
        "GreenArea": "#7AE969",
        "WaterBody": "#5DCFC3",
        "StreetFurniture": "#e04d2d",
        "Playground": "#d11ca2",
        "Building": "#6C8CD5",
        "ResidentialBuilding": "#6C8CD5",
        "ReligiousBuilding": "#6C8CD5",
        "CommercialBuilding": "#22bdbb",
        "School": "#6C8CD5"
    };

    var selectedFeatures = {};
    var selectedLayers = {};

    var mainPnt = {};
    var Xs = [], Ys = [], Ds = [], Xsm = [], Ysm = [], Zs = [];

    for (featureGroupName in featureGroupNames) {
        selectedFeatures[featureGroupName] = {};
        var featureGroup = selectedFeatures[featureGroupName];
        featureGroup.style = "";
        featureGroup.layerGroup = new L.FeatureGroup();
        selectedLayers[featureGroupName] = featureGroup.layerGroup;
        featureGroup.layerGroup.addTo(map);
        featureGroup.objectGroup = {};
        featureGroup.isSelectedOnTheMap = false;
    }

    var population = {};

    // Init border draw toolbar
    var emptyIcon = L.icon({
    	iconUrl: '/images/cursor-icon.svg',
    	iconSize: [0, 0]
    });

    drawControl = new L.Control.Draw({
        draw: {
            polyline: false,
            circle: false,
            circlemarker: false,
            polygon: { repeatMode: false },
            rectangle: { showArea: false, repeatMode: false },
            marker: { icon: emptyIcon, repeatMode: false }
        }
    });
    map.addControl(drawControl);
    $('.leaflet-draw-draw-polygon').attr('title','Draw a complex border');
    $('.leaflet-draw-draw-rectangle').attr('title','Draw a rectangular border');
    $('.leaflet-draw-draw-marker').attr('title','Point to an object');

      var population_age_categories = [
           "До 1 года",
           "1-6",
           "7-18",
           "19-24",
           "25-29",
           "30-34",
           "35-39",
           "40-44",
           "45-49",
           "50-54",
           "55-59",
           "60-64",
           "65-69",
           "старше 70"
        ];
        population_age_values = new Array(population_age_categories.length).fill(0);

    map.on('draw:created', function(drawnItems) {
        var boundaries = drawnItems.layer.toGeoJSON().geometry;
        var cmd = "/findIntersect";
        var parameters = boundaries;
        if(isNeedNeighborGraph && boundaries.type == "Point") {
            cmd = "/findNear";
            parameters = {
                geometry: boundaries,
                radius: $("#amount").val()
            };
        }
        if(isNeedPedestrianGraph && boundaries.type == "Point") {
            cmd = "/findPedestrianGraph";
            parameters = {
                geometry: boundaries,
                radius: $("#amount").val()
            };
        }
        if(isNeedPedestrianConcaveHull && boundaries.type == "Point") {
            cmd = "/findPedestrianConcaveHull";
            parameters = {
                geometry: boundaries,
                radius: $("#amount").val()
            };
        }
        if(isNeedCarGraph && boundaries.type == "Point") {
            cmd = "/findCarGraph";
            parameters = {
                geometry: boundaries,
                radius: $("#amountCar").val()
            };
        }
        if(isNeedCarConcaveHull && boundaries.type == "Point") {
            cmd = "/findCarConcaveHull";
            parameters = {
                geometry: boundaries,
                radius: $("#amountCar").val()
            };
        }
        var xhr = $.ajax({
            type: "POST",
            url: cmd,
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(parameters),
            success: function(data) {
                $.each(data, function(index, object) {
                    var featureGroupName = object.entityType;
                    if (!featureGroupNames.hasOwnProperty(featureGroupName)) {
                        return;
                    }

                    var feature = L.geoJSON(object.geometry, {
                        style: {
                            color: featureGroupNames[featureGroupName],
                            weight: 2
                        }
                    });

                    if(isNeedNeighborGraph && boundaries.type == "Point") {
                        var selected = boundaries.coordinates;
                        var pnt = turf.centroid(object.geometry).geometry.coordinates;
                        var correctPnt, correctSelected;

                        if (object.geometry.type == "LineString" || object.geometry.type == "MultiLineString") {
                            pnt = turf.nearestPointOnLine(object.geometry, selected).geometry.coordinates;
                        }

                        correctPnt = [pnt[1], pnt[0]];
                        correctSelected = [selected[1], selected[0]];
                        let line = L.polyline([correctPnt, correctSelected],
                            {
                                color: featureGroupNames[featureGroupName],
                                weight: 0.25,
                                smoothFactor: 1,
                            });

                        selectedFeatures[featureGroupName].layerGroup.addLayer(line);

                        Xs.push(correctPnt[0]);
                        Ys.push(correctPnt[1]);
                        Ds.push(object.address);
                        Zs.push(turf.distance(correctPnt, correctSelected));
                        mainPnt = correctSelected;
                    }

                    if ($.isEmptyObject(selectedFeatures[featureGroupName].objectGroup)) {
                        $('#entity-list-list').append(
                            '<a href="#' + featureGroupName + '" class="list-group-item list-group-item-action collapsed" data-toggle="collapse">' +
                            '<i class="fa fa-chevron-down"></i><i class="fa fa-chevron-right"></i>' + featureGroupName +
                            '</a>');
                        $('#entity-list-list').append('<ul class="list-group list-group-flush collapse" id=' + featureGroupName + '>');
                    }

                    console.log(object);

                    if (!selectedFeatures[featureGroupName].objectGroup.hasOwnProperty(object.id)) {
                        $('#' + featureGroupName).append(
                            '<a name="' + featureGroupName + object.id + '" href="#' + featureGroupName + object.id + '" class="list-group-item list-group-item-action collapsed" data-toggle="collapse">' +
                            '<i class="fa fa-chevron-down"></i><i class="fa fa-chevron-right"></i>' + object.id +
                            '</a>');
                        var table = '<div id="' + featureGroupName + object.id + '" class="collapse" style="overflow-x: auto;">' +
                            '<table class="table table-sm table-sidebar"><tbody>';
                        var popup = '<table class="table table-sm table-bordered table-dark table-striped" style="margin-bottom: 0px;"><tbody>';

                        $("[name=" + featureGroupName + object.id + "]").on("click", function() {
                            var id = parseInt($(this).attr("name").match(/-?\d+/));
                            var name = $(this).attr("name").match(/[A-Za-z]+/)[0];
                            var obj = selectedFeatures[name].objectGroup[id].geometry;
                            if (obj.type === "Polygon") {
                                  var bounds = obj.coordinates;

                                  var nebnds = [];
                                  bounds[0].forEach(function(element) {
                                             nebnds.push([element[1], element[0]]);
                                           });
                                  map.fitBounds(nebnds);
                            }
                              var center = turf.center(obj).geometry.coordinates;
                              map.panTo([center[1], center[0]]);

                        });

                        for (key in object) {
                            if (key != 'description' && key != 'id' && key != 'entityType' && key != 'geometry') {
                                table += '<tr><td>' + key + '</td><td>' + object[key] + '</td></tr>';
                                popup += '<tr><td>' + key + '</td><td>' + object[key] + '</td></tr>';
                            }
                        }
                        table += '</tbody></table></div>';
                        popup += '</tbody></table></div>';
                        $('#' + featureGroupName).append(table);

                        feature.bindPopup(popup, {
                            closeButton: false
                        });

                        selectedFeatures[featureGroupName].layerGroup.addLayer(feature);
                        selectedFeatures[featureGroupName].objectGroup[object.id] = object;
                    }
                });

                Xsm.push(mainPnt[0]);
                Ysm.push(mainPnt[1]);

                if(isNeedNeighborGraph && boundaries.type == "Point")
                    createGraph();

                if (data.length != 0) {
                    clearSelection.enable();
                    sidebarExpand();
                }
            },
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.message);
            }
        });

        console.log(xhr);

        $.ajax({
            type: "POST",
            url: "/getPopulation",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(boundaries),
            success: function(data) {
                var table = '';
                population_age_categories.forEach(function(item, i) {
                    population_age_values[i] += data[item];
                    table += '<tr><td>' + item + '</td><td>' + population_age_values[i] + '</td></tr>';
                });
                $('#population > table > tbody').html(table);
                createChart();
            }
         });
    });

    var clearSelection = L.easyButton( '<span class="fa fa-trash"></span>', function() {
        for (featureGroupName in selectedFeatures) {
            selectedFeatures[featureGroupName].layerGroup.clearLayers();
            selectedFeatures[featureGroupName].objectGroup = {};
        }
        clearMap();
        Xs = []; Ys = []; Ds = []; Xsm = []; Ysm = []; Zs = [];
        mainPnt = {};
        $("#entity-list-list").empty();
        population_age_values.fill(0);
        $("#population > table > tbody").empty();
        $("#population-age-chart").empty();

        $("#neighbor-graph").empty();
        sidebarCollapse();
        this.disable();
    }, 'Clear selection');
    clearSelection.disable();
    clearSelection.addTo(map);

    // Init layer selection toolbar
    layerControl = L.control.layers({}, selectedLayers).addTo(map);

    selector = $('.leaflet-control-layers-selector + span');

    selector.each(function (index, elem) {
         var featureGroupName = $(elem).text().trim();
         $(elem).after('<input type="color" name="' + featureGroupName + '" value="' + featureGroupNames[featureGroupName] + '"></input>');

         $('input[name=' + featureGroupName + ']').change(function() {
              selectedLayers[this.name].setStyle({color: this.value});
              featureGroupNames[featureGroupName] = this.value;
         });
    });

    selector = $('.leaflet-control-layers-selector');

    selector.each(function (index, elem) {
        $(elem).attr('name', $(elem).next().text().trim());
    });

    $('.leaflet-control-layers-selector').change(function() {
        selectedFeatures[this.name].isSelectedOnTheMap = this.checked;
    });

    function clearMap() {
        for(i in map._layers) {
            if(map._layers[i]._path != undefined) {
                map.removeLayer(map._layers[i]);
            }
        }
    }

    $(document).ready(function() {
        $("#btnBuild").click(function(){
            $("#relations").modal('toggle');

            var toolbar;
            for (var toolbarId in drawControl._toolbars) {
                toolbar = drawControl._toolbars[toolbarId];
                if (toolbar instanceof L.DrawToolbar) {
                    toolbar._modes.marker.handler.enable();
                }
            }

        });
    });

    $(document).ready(function() {
        $("#btnBuildCar").click(function(){
            $("#relations").modal('toggle');

            var toolbar;
            for (var toolbarId in drawControl._toolbars) {
                toolbar = drawControl._toolbars[toolbarId];
                if (toolbar instanceof L.DrawToolbar) {
                    toolbar._modes.marker.handler.enable();
                }
            }

        });
    });

    $('#radio-fa-check-circle-o-1').bind ("click", function() {
        isNeedNeighborGraph = $('#radio-fa-check-circle-o-1').is(':checked');

        if(isNeedNeighborGraph) {
            isNeedPedestrianGraph = false;
            isNeedPedestrianConcaveHull = false;
            isNeedCarGraph = false;
            isNeedCarConcaveHull = false;
            $('#radio-fa-check-circle-o-2').prop('checked', false);
            $('#radio-fa-check-circle-o-3').prop('checked', false);
            $('#radio-fa-check-circle-o-4').prop('checked', false);
            $('#radio-fa-check-circle-o-5').prop('checked', false);
        }
    });

    $('#radio-fa-check-circle-o-2').bind("click", function() {
        isNeedPedestrianGraph = $('#radio-fa-check-circle-o-2').is(':checked');

        if(isNeedPedestrianGraph) {
            isNeedNeighborGraph = false;
            isNeedPedestrianConcaveHull = false;
            isNeedCarGraph = false;
            isNeedCarConcaveHull = false;
            $('#radio-fa-check-circle-o-1').prop('checked', false);
            $('#radio-fa-check-circle-o-3').prop('checked', false);
            $('#radio-fa-check-circle-o-4').prop('checked', false);
            $('#radio-fa-check-circle-o-5').prop('checked', false);
        }
    });

    $('#radio-fa-check-circle-o-3').bind("click", function() {
            isNeedPedestrianConcaveHull = $('#radio-fa-check-circle-o-3').is(':checked');

            if(isNeedPedestrianConcaveHull) {
                isNeedNeighborGraph = false;
                isNeedPedestrianGraph = false;
                isNeedCarGraph = false;
                isNeedCarConcaveHull = false;
                $('#radio-fa-check-circle-o-1').prop('checked', false);
                $('#radio-fa-check-circle-o-2').prop('checked', false);
                $('#radio-fa-check-circle-o-4').prop('checked', false);
                $('#radio-fa-check-circle-o-5').prop('checked', false);
            }
        });

    $('#radio-fa-check-circle-o-4').bind("click", function() {
        isNeedCarGraph = $('#radio-fa-check-circle-o-4').is(':checked');

        if(isNeedCarGraph) {
            isNeedNeighborGraph = false;
            isNeedPedestrianConcaveHull = false;
            isNeedPedestrianGraph = false;
            isNeedCarConcaveHull = false;
            $('#radio-fa-check-circle-o-1').prop('checked', false);
            $('#radio-fa-check-circle-o-3').prop('checked', false);
            $('#radio-fa-check-circle-o-2').prop('checked', false);
            $('#radio-fa-check-circle-o-5').prop('checked', false);
        }
    });

    $('#radio-fa-check-circle-o-5').bind("click", function() {
        isNeedCarConcaveHull = $('#radio-fa-check-circle-o-5').is(':checked');

        if(isNeedCarConcaveHull) {
            isNeedNeighborGraph = false;
            isNeedPedestrianGraph = false;
            isNeedPedestrianConcaveHull = false;
            isNeedCarGraph = false;
            $('#radio-fa-check-circle-o-1').prop('checked', false);
            $('#radio-fa-check-circle-o-2').prop('checked', false);
            $('#radio-fa-check-circle-o-3').prop('checked', false);
            $('#radio-fa-check-circle-o-4').prop('checked', false);
        }
    });


    // Utils
    function sidebarExpand() {
        $('.sidebar').addClass('show');
        $('.leaflet-control-layers').css('transform', 'translateX(-250px)');
    }

    function sidebarCollapse() {
        $('.sidebar').removeClass('show');
        $('.leaflet-control-layers').css('transform', 'translateX(0px)');
    }

    $('#showSelection').on('click', function(){
        if ($('.sidebar').hasClass('show')) {
            sidebarCollapse();
        } else {
            sidebarExpand();
        }
    });

    function createGraph() {
        var trace1 = {
            x: Ysm,
            y: Xsm,
            mode: 'markers',
            type: 'scatter',
            marker: { size: 10, color: 'red' }
        };

        var trace2 = {
            dragmode: 'zoom',
            x: Ys,
            y: Xs,
            mode: 'markers',
            type: 'scatter',
            text: Ds,
            marker: { size: 5, color: 'blue' }
        };

        var data = [];

        for(var i = 0; i < Xs.length; i++) {
            var result = {
                type: 'scatter',
                x: [ mainPnt[1] , Ys[i] ],
                y: [ mainPnt[0] , Xs[i] ],
                mode: 'lines',
                opacity: Zs[i],
                line: {
                    width: 1,
                    color: 'grey'
                }
            };

            data.push(result);
        }

        var layout = {
            showlegend: false,
            xaxis: {
                range: [ Math.min.apply(Math, Ys), Math.max.apply(Math, Ys) ]
            },
            yaxis: {
                range: [ Math.min.apply(Math, Xs), Math.max.apply(Math, Xs) ]
            },
            dtick: 0.005,
            autosize: false,
            width: 450,
            height: 450
        };

        data.push(trace1, trace2);
        Plotly.newPlot('neighbor-graph', data, layout);
    }

    function createChart() {
          var chart;

          $('#population-age-chart').highcharts({
            chart: {
              type: 'column',
              margin: [60, 10, 60, 40]
            },
            title: {
              text: 'Age Distribution'
            },
            legend: {
              enabled: false
            },
            credits: {
              enabled: false
            },
            exporting: {
              enabled: false
            },
            tooltip: {},
            plotOptions: {
              series: {
                pointPadding: 0,
                groupPadding: 0,
                borderWidth: 0.5,
                borderColor: 'rgba(255,255,255,0.5)',
                color: "#FFA15B"
              }
            },
            xAxis: {
              categories: population_age_categories,
              title: {
                text: 'Age'
              },
              min: 0
            },
            yAxis: {
              title: {
                text: 'Population'
              },
              min: 0
            }
          });
          chart = $('#population-age-chart').highcharts();
          chart.addSeries({
            name: 'Number',
            data: population_age_values
          });
    }

    // Add layer search panel
    L.LayerGroup.include({
        customGetLayer: function(id) {
            for (var i in this._layers) {
                if (this._layers[i].options.id == id) {
                    return this._layers[i];
                }
            }
        }
    });

    var layers = new L.LayerGroup();
    layers.addTo(map);

    $('input#layer-search-input').autocomplete({
        source: function(request, response) {
            $.ajax({
                type: "GET",
                url: "/findLayers",
                contentType: "application/json",
                dataType: "json",
                data: {
                    startsWith: request.term,
                    maxRows: 3
                },
                success: function(data) {
                    response($.map(data, function(layer) {
                        return {
                            label: layer.name,
                            value: layer.name
                        }
                    }));
                }
            });
        },
        minLength: 1
    });

    $('#srch-button').click(function(e) {
        e.preventDefault();
        layers.clearLayers();
        $("#layer-list .list-group").empty();
        var term = $('#layer-search-input').val();
        $.ajax({
            type: "GET",
            url: "/findLayers",
            data: {
                startsWith: term,
                maxRows: 20
            },
            success: function(data) {
                $.each(data, function(i, item) {
                    var itemId = 'layer' + item.id;
                    var layer = '<li class="list-group-item">' +
                                    '<div class="form-check">' +
                                        '<input class="form-check-input layer-checkbox" type="checkbox" id="' + itemId + '">' +
                                        '<label class="form-check-label" for="' + itemId + '">' + item.name + '</label>' +
                                    '</div>' +
                                '</li>';
                    $('#layer-list .list-group').append(layer);
                });
                $(".layer-checkbox").change(function() {
                    if (this.checked) {
                        var id = parseInt(this.id.match(/\d+/));
                        $.ajax({
                            type: "GET",
                            url: "/getLayer",
                            data: {
                                id: id
                            },
                            success: function(data) {
                                var currLayer = L.geoJSON(JSON.parse(data.info));
                                currLayer.id = id;
                                layers.addLayer(currLayer);
                            }
                        });
                    } else {
                        layers.removeLayer(layers.customGetLayer(id));
                    }
                });
            }
        });
    });
});