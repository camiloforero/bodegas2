define(['model/pedidoModel'], function(pedidoModel) {
    App.Controller._PedidoController = Backbone.View.extend({
        initialize: function(options) {
            this.modelClass = options.modelClass;
            this.listModelClass = options.listModelClass;
            this.showEdit = true;
            this.showDelete = true;
            this.editTemplate = _.template($('#pedido').html());
            this.listTemplate = _.template($('#pedidoList').html());
            if (!options || !options.componentId) {
                this.componentId = _.random(0, 100) + "";
            }else{
				this.componentId = options.componentId;
		    }
            var self = this;
            Backbone.on(this.componentId + '-' + 'pedido-create', function(params) {
                self.create(params);
            });
            Backbone.on(this.componentId + '-' + 'pedido-list', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'pedido-edit', function(params) {
                self.edit(params);
            });
            Backbone.on(this.componentId + '-' + 'pedido-delete', function(params) {
                self.destroy(params);
            });
            Backbone.on(this.componentId + '-' + 'post-pedido-delete', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'pedido-save', function(params) {
                self.save(params);
            });
            if(self.postInit){
            	self.postInit();
            }
        },
        create: function() {
            if (App.Utils.eventExists(this.componentId + '-' +'instead-pedido-create')) {
                Backbone.trigger(this.componentId + '-' + 'instead-pedido-create', {view: this});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-pedido-create', {view: this});
                this.currentPedidoModel = new this.modelClass();
                this._renderEdit();
                Backbone.trigger(this.componentId + '-' + 'post-pedido-create', {view: this});
            }
        },
        list: function(params) {
            if (params) {
                var data = params.data;
            }
            if (App.Utils.eventExists(this.componentId + '-' +'instead-pedido-list')) {
                Backbone.trigger(this.componentId + '-' + 'instead-pedido-list', {view: this, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-pedido-list', {view: this, data: data});
                var self = this;
				if(!this.pedidoModelList){
                 this.pedidoModelList = new this.listModelClass();
				}
                this.pedidoModelList.fetch({
                    data: data,
                    success: function() {
                        self._renderList();
                        Backbone.trigger(self.componentId + '-' + 'post-pedido-list', {view: self});
                    },
                    error: function(mode, error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'pedido-list', view: self, error: error});
                    }
                });
            }
        },
        edit: function(params) {
            var id = params.id;
            var data = params.data;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-pedido-edit')) {
                Backbone.trigger(this.componentId + '-' + 'instead-pedido-edit', {view: this, id: id, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-pedido-edit', {view: this, id: id, data: data});
                if (this.pedidoModelList) {
                    this.currentPedidoModel = this.pedidoModelList.get(id);
                    this._renderEdit();
                    Backbone.trigger(this.componentId + '-' + 'post-pedido-edit', {view: this, id: id, data: data});
                } else {
                    var self = this;
                    this.currentPedidoModel = new this.modelClass({id: id});
                    this.currentPedidoModel.fetch({
                        data: data,
                        success: function() {
                            self._renderEdit();
                            Backbone.trigger(self.componentId + '-' + 'post-pedido-edit', {view: this, id: id, data: data});
                        },
                        error: function() {
                            Backbone.trigger(self.componentId + '-' + 'error', {event: 'pedido-edit', view: self, id: id, data: data, error: error});
                        }
                    });
                }
            }
        },
        destroy: function(params) {
            var id = params.id;
            var self = this;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-pedido-delete')) {
                Backbone.trigger(this.componentId + '-' + 'instead-pedido-delete', {view: this, id: id});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-pedido-delete', {view: this, id: id});
                var deleteModel;
                if (this.pedidoModelList) {
                    deleteModel = this.pedidoModelList.get(id);
                } else {
                    deleteModel = new this.modelClass({id: id});
                }
                deleteModel.destroy({
                    success: function() {
                        Backbone.trigger(self.componentId + '-' + 'post-pedido-delete', {view: self, model: deleteModel});
                    },
                    error: function() {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'pedido-delete', view: self, error: error});
                    }
                });
            }
        },
		_loadRequiredComponentsData: function(callBack) {
            var self = this;
            var listReady = _.after(1, function(){
                callBack();
            }); 
            var listDataReady = function(componentName, model){
                self[componentName] = model;
                listReady();
            };
				App.Utils.getComponentList('productoComponent',listDataReady);
        },
        save: function() {
            var self = this;
            var model = $('#' + this.componentId + '-pedidoForm').serializeObject();
            if (App.Utils.eventExists(this.componentId + '-' +'instead-pedido-save')) {
                Backbone.trigger(this.componentId + '-' + 'instead-pedido-save', {view: this, model : model});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-pedido-save', {view: this, model : model});
                this.currentPedidoModel.set(model);
                this.currentPedidoModel.save({},
                        {
                            success: function(model) {
                                Backbone.trigger(self.componentId + '-' + 'post-pedido-save', {model: self.currentPedidoModel});
                            },
                            error: function(error) {
                                Backbone.trigger(self.componentId + '-' + 'error', {event: 'pedido-save', view: self, error: error});
                            }
                        });
            }
        },
        _renderList: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.listTemplate({pedidos: self.pedidoModelList.models, componentId: self.componentId, showEdit : self.showEdit , showDelete : self.showDelete}));
                self.$el.slideDown("fast");
            });
        },
        _renderEdit: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.editTemplate({pedido: self.currentPedidoModel, componentId: self.componentId , showEdit : self.showEdit , showDelete : self.showDelete
 
				    ,producto: self.productoComponent
 
				}));
                self.$el.slideDown("fast");
            });
        }
    });
    return App.Controller._PedidoController;
});