define(['model/ordenModel'], function(ordenModel) {
    App.Controller._OrdenController = Backbone.View.extend({
        initialize: function(options) {
            this.modelClass = options.modelClass;
            this.listModelClass = options.listModelClass;
            this.showEdit = true;
            this.showDelete = true;
            this.editTemplate = _.template($('#orden').html());
            this.listTemplate = _.template($('#ordenList').html());
            if (!options || !options.componentId) {
                this.componentId = _.random(0, 100) + "";
            }else{
				this.componentId = options.componentId;
		    }
            var self = this;
            Backbone.on(this.componentId + '-' + 'orden-create', function(params) {
                self.create(params);
            });
            Backbone.on(this.componentId + '-' + 'orden-list', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'orden-edit', function(params) {
                self.edit(params);
            });
            Backbone.on(this.componentId + '-' + 'orden-delete', function(params) {
                self.destroy(params);
            });
            Backbone.on(this.componentId + '-' + 'post-orden-delete', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'orden-save', function(params) {
                self.save(params);
            });
            if(self.postInit){
            	self.postInit();
            }
        },
        create: function() {
            if (App.Utils.eventExists(this.componentId + '-' +'instead-orden-create')) {
                Backbone.trigger(this.componentId + '-' + 'instead-orden-create', {view: this});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-orden-create', {view: this});
                this.currentOrdenModel = new this.modelClass();
                this._renderEdit();
                Backbone.trigger(this.componentId + '-' + 'post-orden-create', {view: this});
            }
        },
        list: function(params) {
            if (params) {
                var data = params.data;
            }
            if (App.Utils.eventExists(this.componentId + '-' +'instead-orden-list')) {
                Backbone.trigger(this.componentId + '-' + 'instead-orden-list', {view: this, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-orden-list', {view: this, data: data});
                var self = this;
				if(!this.ordenModelList){
                 this.ordenModelList = new this.listModelClass();
				}
                this.ordenModelList.fetch({
                    data: data,
                    success: function() {
                        self._renderList();
                        Backbone.trigger(self.componentId + '-' + 'post-orden-list', {view: self});
                    },
                    error: function(mode, error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'orden-list', view: self, error: error});
                    }
                });
            }
        },
        edit: function(params) {
            var id = params.id;
            var data = params.data;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-orden-edit')) {
                Backbone.trigger(this.componentId + '-' + 'instead-orden-edit', {view: this, id: id, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-orden-edit', {view: this, id: id, data: data});
                if (this.ordenModelList) {
                    this.currentOrdenModel = this.ordenModelList.get(id);
                    this._renderEdit();
                    Backbone.trigger(this.componentId + '-' + 'post-orden-edit', {view: this, id: id, data: data});
                } else {
                    var self = this;
                    this.currentOrdenModel = new this.modelClass({id: id});
                    this.currentOrdenModel.fetch({
                        data: data,
                        success: function() {
                            self._renderEdit();
                            Backbone.trigger(self.componentId + '-' + 'post-orden-edit', {view: this, id: id, data: data});
                        },
                        error: function() {
                            Backbone.trigger(self.componentId + '-' + 'error', {event: 'orden-edit', view: self, id: id, data: data, error: error});
                        }
                    });
                }
            }
        },
        destroy: function(params) {
            var id = params.id;
            var self = this;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-orden-delete')) {
                Backbone.trigger(this.componentId + '-' + 'instead-orden-delete', {view: this, id: id});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-orden-delete', {view: this, id: id});
                var deleteModel;
                if (this.ordenModelList) {
                    deleteModel = this.ordenModelList.get(id);
                } else {
                    deleteModel = new this.modelClass({id: id});
                }
                deleteModel.destroy({
                    success: function() {
                        Backbone.trigger(self.componentId + '-' + 'post-orden-delete', {view: self, model: deleteModel});
                    },
                    error: function() {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'orden-delete', view: self, error: error});
                    }
                });
            }
        },
        save: function() {
            var self = this;
            var model = $('#' + this.componentId + '-ordenForm').serializeObject();
            if (App.Utils.eventExists(this.componentId + '-' +'instead-orden-save')) {
                Backbone.trigger(this.componentId + '-' + 'instead-orden-save', {view: this, model : model});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-orden-save', {view: this, model : model});
                this.currentOrdenModel.set(model);
                this.currentOrdenModel.save({},
                        {
                            success: function(model) {
                                Backbone.trigger(self.componentId + '-' + 'post-orden-save', {model: self.currentOrdenModel});
                            },
                            error: function(error) {
                                Backbone.trigger(self.componentId + '-' + 'error', {event: 'orden-save', view: self, error: error});
                            }
                        });
            }
        },
        _renderList: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.listTemplate({ordens: self.ordenModelList.models, componentId: self.componentId, showEdit : self.showEdit , showDelete : self.showDelete}));
                self.$el.slideDown("fast");
            });
        },
        _renderEdit: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.editTemplate({orden: self.currentOrdenModel, componentId: self.componentId , showEdit : self.showEdit , showDelete : self.showDelete
 
				}));
                self.$el.slideDown("fast");
            });
        }
    });
    return App.Controller._OrdenController;
});