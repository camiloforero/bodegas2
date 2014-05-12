define(['controller/selectionController', 'model/cacheModel', 'model/bodegaMasterModel', 'component/_CRUDComponent', 'controller/tabController', 'component/bodegaComponent',
 'component/itemInventarioComponent'
 
 ],function(SelectionController, CacheModel, BodegaMasterModel, CRUDComponent, TabController, BodegaComponent,
 ItemInventarioComponent
 ) {
    App.Component.BodegaMasterComponent = App.Component.BasicComponent.extend({
        initialize: function() {
            var self = this;
            this.configuration = App.Utils.loadComponentConfiguration('bodegaMaster');
            var uComponent = new BodegaComponent();
            uComponent.initialize();
            uComponent.render('main');
            Backbone.on(uComponent.componentId + '-post-bodega-create', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-post-bodega-edit', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-pre-bodega-list', function() {
                self.hideChilds();
            });
            Backbone.on('bodega-master-model-error', function(error) {
                Backbone.trigger(uComponent.componentId + '-' + 'error', {event: 'bodega-master-save', view: self, error: error});
            });
            Backbone.on(uComponent.componentId + '-instead-bodega-save', function(params) {
                self.model.set('bodegaEntity', params.model);
                if (params.model) {
                    self.model.set('id', params.model.id);
                } else {
                    self.model.unset('id');
                }
                var itemInventarioModels = self.itemInventarioComponent.componentController.itemInventarioModelList;
                self.model.set('listItemInventario', []);
                self.model.set('createItemInventario', []);
                self.model.set('updateItemInventario', []);
                self.model.set('deleteItemInventario', []);
                for (var i = 0; i < itemInventarioModels.models.length; i++) {
                    var m = itemInventarioModels.models[i];
                    var modelCopy = m.clone();
                    if (m.isCreated()) {
                        //set the id to null
                        modelCopy.unset('id');
                        self.model.get('createItemInventario').push(modelCopy.toJSON());
                    } else if (m.isUpdated()) {
                        self.model.get('updateItemInventario').push(modelCopy.toJSON());
                    }
                }
                for (var i = 0; i < itemInventarioModels.deletedModels.length; i++) {
                    var m = itemInventarioModels.deletedModels[i];
                    self.model.get('deleteItemInventario').push(m.toJSON());
                }
                self.model.save({}, {
                    success: function() {
                        uComponent.componentController.list();
                    },
                    error: function(error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'bodega-master-save', view: self, error: error});
                    }
                });
            });
        },
        renderChilds: function(params) {
            var self = this;
            this.tabModel = new App.Model.TabModel(
                    {
                        tabs: [
                            {label: "ItemInventario", name: "itemInventario", enable: true},
                        ]
                    }
            );

            this.tabs = new TabController({model: this.tabModel});

            this.tabs.render('tabs');
            App.Model.BodegaMasterModel.prototype.urlRoot = this.configuration.context;
            var options = {
                success: function() {
					self.itemInventarioComponent = new ItemInventarioComponent();
                    self.itemInventarioModels = App.Utils.convertToModel(App.Utils.createCacheModel(App.Model.ItemInventarioModel), self.model.get('listItemInventario'));
                    self.itemInventarioComponent.initialize({
                        modelClass: App.Utils.createCacheModel(App.Model.ItemInventarioModel),
                        listModelClass: App.Utils.createCacheList(App.Model.ItemInventarioModel, App.Model.ItemInventarioList, self.itemInventarioModels)
                    });
                    self.itemInventarioComponent.render(self.tabs.getTabHtmlId('itemInventario'));
                    Backbone.on(self.itemInventarioComponent.componentId + '-post-itemInventario-create', function(params) {
                        params.view.currentItemInventarioModel.setCacheList(params.view.itemInventarioModelList);
                    });
                    self.itemInventarioToolbarModel = self.itemInventarioComponent.toolbarModel.set(App.Utils.Constans.referenceToolbarConfiguration);
                    self.itemInventarioComponent.setToolbarModel(self.itemInventarioToolbarModel);                    
                	
                     
                
                    $('#tabs').show();
                },
                error: function() {
                    Backbone.trigger(self.componentId + '-' + 'error', {event: 'bodega-edit', view: self, id: id, data: data, error: error});
                }
            };
            if (params.id) {
                self.model = new App.Model.BodegaMasterModel({id: params.id});
                self.model.fetch(options);
            } else {
                self.model = new App.Model.BodegaMasterModel();
                options.success();
            }


        },
        hideChilds: function() {
            $('#tabs').hide();
        }
    });

    return App.Component.BodegaMasterComponent;
});