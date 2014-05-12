define(['model/bodegaModel'], function(bodegaModel) {
    App.Controller._BodegaController = Backbone.View.extend({
        initialize: function(options) {
            this.modelClass = options.modelClass;
            this.listModelClass = options.listModelClass;
            this.showEdit = true;
            this.showDelete = true;
            this.editTemplate = _.template($('#bodega').html());
            this.listTemplate = _.template($('#bodegaList').html());
            if (!options || !options.componentId) {
                this.componentId = _.random(0, 100) + "";
            }else{
				this.componentId = options.componentId;
		    }
            var self = this;
            Backbone.on(this.componentId + '-' + 'bodega-create', function(params) {
                self.create(params);
            });
            Backbone.on(this.componentId + '-' + 'bodega-list', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'bodega-edit', function(params) {
                self.edit(params);
            });
            Backbone.on(this.componentId + '-' + 'bodega-delete', function(params) {
                self.destroy(params);
            });
            Backbone.on(this.componentId + '-' + 'post-bodega-delete', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'bodega-save', function(params) {
                self.save(params);
            });
            if(self.postInit){
            	self.postInit();
            }
        },
        create: function() {
            if (App.Utils.eventExists(this.componentId + '-' +'instead-bodega-create')) {
                Backbone.trigger(this.componentId + '-' + 'instead-bodega-create', {view: this});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-bodega-create', {view: this});
                this.currentBodegaModel = new this.modelClass();
                this._renderEdit();
                Backbone.trigger(this.componentId + '-' + 'post-bodega-create', {view: this});
            }
        },
        list: function(params) {
            if (params) {
                var data = params.data;
            }
            if (App.Utils.eventExists(this.componentId + '-' +'instead-bodega-list')) {
                Backbone.trigger(this.componentId + '-' + 'instead-bodega-list', {view: this, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-bodega-list', {view: this, data: data});
                var self = this;
				if(!this.bodegaModelList){
                 this.bodegaModelList = new this.listModelClass();
				}
                this.bodegaModelList.fetch({
                    data: data,
                    success: function() {
                        self._renderList();
                        Backbone.trigger(self.componentId + '-' + 'post-bodega-list', {view: self});
                    },
                    error: function(mode, error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'bodega-list', view: self, error: error});
                    }
                });
            }
        },
        edit: function(params) {
            var id = params.id;
            var data = params.data;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-bodega-edit')) {
                Backbone.trigger(this.componentId + '-' + 'instead-bodega-edit', {view: this, id: id, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-bodega-edit', {view: this, id: id, data: data});
                if (this.bodegaModelList) {
                    this.currentBodegaModel = this.bodegaModelList.get(id);
                    this._renderEdit();
                    Backbone.trigger(this.componentId + '-' + 'post-bodega-edit', {view: this, id: id, data: data});
                } else {
                    var self = this;
                    this.currentBodegaModel = new this.modelClass({id: id});
                    this.currentBodegaModel.fetch({
                        data: data,
                        success: function() {
                            self._renderEdit();
                            Backbone.trigger(self.componentId + '-' + 'post-bodega-edit', {view: this, id: id, data: data});
                        },
                        error: function() {
                            Backbone.trigger(self.componentId + '-' + 'error', {event: 'bodega-edit', view: self, id: id, data: data, error: error});
                        }
                    });
                }
            }
        },
        destroy: function(params) {
            var id = params.id;
            var self = this;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-bodega-delete')) {
                Backbone.trigger(this.componentId + '-' + 'instead-bodega-delete', {view: this, id: id});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-bodega-delete', {view: this, id: id});
                var deleteModel;
                if (this.bodegaModelList) {
                    deleteModel = this.bodegaModelList.get(id);
                } else {
                    deleteModel = new this.modelClass({id: id});
                }
                deleteModel.destroy({
                    success: function() {
                        Backbone.trigger(self.componentId + '-' + 'post-bodega-delete', {view: self, model: deleteModel});
                    },
                    error: function() {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'bodega-delete', view: self, error: error});
                    }
                });
            }
        },
        save: function() {
            var self = this;
            var model = $('#' + this.componentId + '-bodegaForm').serializeObject();
            if (App.Utils.eventExists(this.componentId + '-' +'instead-bodega-save')) {
                Backbone.trigger(this.componentId + '-' + 'instead-bodega-save', {view: this, model : model});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-bodega-save', {view: this, model : model});
                this.currentBodegaModel.set(model);
                this.currentBodegaModel.save({},
                        {
                            success: function(model) {
                                Backbone.trigger(self.componentId + '-' + 'post-bodega-save', {model: self.currentBodegaModel});
                            },
                            error: function(error) {
                                Backbone.trigger(self.componentId + '-' + 'error', {event: 'bodega-save', view: self, error: error});
                            }
                        });
            }
        },
        _renderList: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.listTemplate({bodegas: self.bodegaModelList.models, componentId: self.componentId, showEdit : self.showEdit , showDelete : self.showDelete}));
                self.$el.slideDown("fast");
            });
        },
        _renderEdit: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.editTemplate({bodega: self.currentBodegaModel, componentId: self.componentId , showEdit : self.showEdit , showDelete : self.showDelete
 
				}));
                self.$el.slideDown("fast");
            });
        }
    });
    return App.Controller._BodegaController;
});