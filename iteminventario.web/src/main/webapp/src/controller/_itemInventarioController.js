define(['model/itemInventarioModel'], function(itemInventarioModel) {
    App.Controller._ItemInventarioController = Backbone.View.extend({
        initialize: function(options) {
            this.modelClass = options.modelClass;
            this.listModelClass = options.listModelClass;
            this.showEdit = true;
            this.showDelete = true;
            this.editTemplate = _.template($('#itemInventario').html());
            this.listTemplate = _.template($('#itemInventarioList').html());
            if (!options || !options.componentId) {
                this.componentId = _.random(0, 100) + "";
            }else{
				this.componentId = options.componentId;
		    }
            var self = this;
            Backbone.on(this.componentId + '-' + 'itemInventario-create', function(params) {
                self.create(params);
            });
            Backbone.on(this.componentId + '-' + 'itemInventario-list', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'itemInventario-edit', function(params) {
                self.edit(params);
            });
            Backbone.on(this.componentId + '-' + 'itemInventario-delete', function(params) {
                self.destroy(params);
            });
            Backbone.on(this.componentId + '-' + 'post-itemInventario-delete', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'itemInventario-save', function(params) {
                self.save(params);
            });
            if(self.postInit){
            	self.postInit();
            }
        },
        create: function() {
            if (App.Utils.eventExists(this.componentId + '-' +'instead-itemInventario-create')) {
                Backbone.trigger(this.componentId + '-' + 'instead-itemInventario-create', {view: this});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-itemInventario-create', {view: this});
                this.currentItemInventarioModel = new this.modelClass();
                this._renderEdit();
                Backbone.trigger(this.componentId + '-' + 'post-itemInventario-create', {view: this});
            }
        },
        list: function(params) {
            if (params) {
                var data = params.data;
            }
            if (App.Utils.eventExists(this.componentId + '-' +'instead-itemInventario-list')) {
                Backbone.trigger(this.componentId + '-' + 'instead-itemInventario-list', {view: this, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-itemInventario-list', {view: this, data: data});
                var self = this;
				if(!this.itemInventarioModelList){
                 this.itemInventarioModelList = new this.listModelClass();
				}
                this.itemInventarioModelList.fetch({
                    data: data,
                    success: function() {
                        self._renderList();
                        Backbone.trigger(self.componentId + '-' + 'post-itemInventario-list', {view: self});
                    },
                    error: function(mode, error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'itemInventario-list', view: self, error: error});
                    }
                });
            }
        },
        edit: function(params) {
            var id = params.id;
            var data = params.data;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-itemInventario-edit')) {
                Backbone.trigger(this.componentId + '-' + 'instead-itemInventario-edit', {view: this, id: id, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-itemInventario-edit', {view: this, id: id, data: data});
                if (this.itemInventarioModelList) {
                    this.currentItemInventarioModel = this.itemInventarioModelList.get(id);
                    this._renderEdit();
                    Backbone.trigger(this.componentId + '-' + 'post-itemInventario-edit', {view: this, id: id, data: data});
                } else {
                    var self = this;
                    this.currentItemInventarioModel = new this.modelClass({id: id});
                    this.currentItemInventarioModel.fetch({
                        data: data,
                        success: function() {
                            self._renderEdit();
                            Backbone.trigger(self.componentId + '-' + 'post-itemInventario-edit', {view: this, id: id, data: data});
                        },
                        error: function() {
                            Backbone.trigger(self.componentId + '-' + 'error', {event: 'itemInventario-edit', view: self, id: id, data: data, error: error});
                        }
                    });
                }
            }
        },
        destroy: function(params) {
            var id = params.id;
            var self = this;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-itemInventario-delete')) {
                Backbone.trigger(this.componentId + '-' + 'instead-itemInventario-delete', {view: this, id: id});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-itemInventario-delete', {view: this, id: id});
                var deleteModel;
                if (this.itemInventarioModelList) {
                    deleteModel = this.itemInventarioModelList.get(id);
                } else {
                    deleteModel = new this.modelClass({id: id});
                }
                deleteModel.destroy({
                    success: function() {
                        Backbone.trigger(self.componentId + '-' + 'post-itemInventario-delete', {view: self, model: deleteModel});
                    },
                    error: function() {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'itemInventario-delete', view: self, error: error});
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
            var model = $('#' + this.componentId + '-itemInventarioForm').serializeObject();
            if (App.Utils.eventExists(this.componentId + '-' +'instead-itemInventario-save')) {
                Backbone.trigger(this.componentId + '-' + 'instead-itemInventario-save', {view: this, model : model});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-itemInventario-save', {view: this, model : model});
                this.currentItemInventarioModel.set(model);
                this.currentItemInventarioModel.save({},
                        {
                            success: function(model) {
                                Backbone.trigger(self.componentId + '-' + 'post-itemInventario-save', {model: self.currentItemInventarioModel});
                            },
                            error: function(error) {
                                Backbone.trigger(self.componentId + '-' + 'error', {event: 'itemInventario-save', view: self, error: error});
                            }
                        });
            }
        },
        _renderList: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.listTemplate({itemInventarios: self.itemInventarioModelList.models, componentId: self.componentId, showEdit : self.showEdit , showDelete : self.showDelete}));
                self.$el.slideDown("fast");
            });
        },
        _renderEdit: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.editTemplate({itemInventario: self.currentItemInventarioModel, componentId: self.componentId , showEdit : self.showEdit , showDelete : self.showDelete
 
				    ,producto: self.productoComponent
 
				}));
                self.$el.slideDown("fast");
            });
        }
    });
    return App.Controller._ItemInventarioController;
});