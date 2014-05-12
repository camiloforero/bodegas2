define(['controller/selectionController', 'model/cacheModel', 'model/pedidoMasterModel', 'component/_CRUDComponent', 'controller/tabController', 'component/pedidoComponent',
 'component/ordenComponent'
 
 ],function(SelectionController, CacheModel, PedidoMasterModel, CRUDComponent, TabController, PedidoComponent,
 OrdenComponent
 ) {
    App.Component.PedidoMasterComponent = App.Component.BasicComponent.extend({
        initialize: function() {
            var self = this;
            this.configuration = App.Utils.loadComponentConfiguration('pedidoMaster');
            var uComponent = new PedidoComponent();
            uComponent.initialize();
            uComponent.render('main');
            Backbone.on(uComponent.componentId + '-post-pedido-create', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-post-pedido-edit', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(uComponent.componentId + '-pre-pedido-list', function() {
                self.hideChilds();
            });
            Backbone.on('pedido-master-model-error', function(error) {
                Backbone.trigger(uComponent.componentId + '-' + 'error', {event: 'pedido-master-save', view: self, error: error});
            });
            Backbone.on(uComponent.componentId + '-instead-pedido-save', function(params) {
                self.model.set('pedidoEntity', params.model);
                if (params.model) {
                    self.model.set('id', params.model.id);
                } else {
                    self.model.unset('id');
                }
                var ordenModels = self.ordenComponent.componentController.ordenModelList;
                self.model.set('listOrden', []);
                self.model.set('createOrden', []);
                self.model.set('updateOrden', []);
                self.model.set('deleteOrden', []);
                for (var i = 0; i < ordenModels.models.length; i++) {
                    var m = ordenModels.models[i];
                    var modelCopy = m.clone();
                    if (m.isCreated()) {
                        //set the id to null
                        modelCopy.unset('id');
                        self.model.get('createOrden').push(modelCopy.toJSON());
                    } else if (m.isUpdated()) {
                        self.model.get('updateOrden').push(modelCopy.toJSON());
                    }
                }
                for (var i = 0; i < ordenModels.deletedModels.length; i++) {
                    var m = ordenModels.deletedModels[i];
                    self.model.get('deleteOrden').push(m.toJSON());
                }
                self.model.save({}, {
                    success: function() {
                        uComponent.componentController.list();
                    },
                    error: function(error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'pedido-master-save', view: self, error: error});
                    }
                });
            });
        },
        renderChilds: function(params) {
            var self = this;
            this.tabModel = new App.Model.TabModel(
                    {
                        tabs: [
                            {label: "Orden", name: "orden", enable: true},
                        ]
                    }
            );

            this.tabs = new TabController({model: this.tabModel});

            this.tabs.render('tabs');
            App.Model.PedidoMasterModel.prototype.urlRoot = this.configuration.context;
            var options = {
                success: function() {
					self.ordenComponent = new OrdenComponent();
                    self.ordenModels = App.Utils.convertToModel(App.Utils.createCacheModel(App.Model.OrdenModel), self.model.get('listOrden'));
                    self.ordenComponent.initialize({
                        modelClass: App.Utils.createCacheModel(App.Model.OrdenModel),
                        listModelClass: App.Utils.createCacheList(App.Model.OrdenModel, App.Model.OrdenList, self.ordenModels)
                    });
                    self.ordenComponent.render(self.tabs.getTabHtmlId('orden'));
                    Backbone.on(self.ordenComponent.componentId + '-post-orden-create', function(params) {
                        params.view.currentOrdenModel.setCacheList(params.view.ordenModelList);
                    });
                    self.ordenToolbarModel = self.ordenComponent.toolbarModel.set(App.Utils.Constans.referenceToolbarConfiguration);
                    self.ordenComponent.setToolbarModel(self.ordenToolbarModel);                    
                	
                     
                
                    $('#tabs').show();
                },
                error: function() {
                    Backbone.trigger(self.componentId + '-' + 'error', {event: 'pedido-edit', view: self, id: id, data: data, error: error});
                }
            };
            if (params.id) {
                self.model = new App.Model.PedidoMasterModel({id: params.id});
                self.model.fetch(options);
            } else {
                self.model = new App.Model.PedidoMasterModel();
                options.success();
            }


        },
        hideChilds: function() {
            $('#tabs').hide();
        }
    });

    return App.Component.PedidoMasterComponent;
});