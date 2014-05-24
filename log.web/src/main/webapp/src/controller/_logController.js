define(['model/logModel'], function(logModel) {
    App.Controller._LogController = Backbone.View.extend({
        initialize: function(options) {
            this.modelClass = options.modelClass;
            this.listModelClass = options.listModelClass;
            this.showEdit = true;
            this.showDelete = true;
            this.editTemplate = _.template($('#log').html());
            this.listTemplate = _.template($('#logList').html());
            if (!options || !options.componentId) {
                this.componentId = _.random(0, 100) + "";
            }else{
				this.componentId = options.componentId;
		    }
            var self = this;
            Backbone.on(this.componentId + '-' + 'log-create', function(params) {
                self.create(params);
            });
            Backbone.on(this.componentId + '-' + 'log-list', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'log-edit', function(params) {
                self.edit(params);
            });
            Backbone.on(this.componentId + '-' + 'log-delete', function(params) {
                self.destroy(params);
            });
            Backbone.on(this.componentId + '-' + 'post-log-delete', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'log-save', function(params) {
                self.save(params);
            });
            Backbone.on(this.componentId + '-' + 'toolbar-search', function(params) {
                self.reaprov(params);
            });
            Backbone.on(this.componentId + '-' + 'toolbar-print', function(params) {
                window.location = "http://localhost:8383/EquipoRocketIndex/public_html/index.html";
            });
            if(self.postInit){
            	self.postInit();
            }
        },
        create: function() {
            if (App.Utils.eventExists(this.componentId + '-' +'instead-log-create')) {
                Backbone.trigger(this.componentId + '-' + 'instead-log-create', {view: this});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-log-create', {view: this});
                this.currentLogModel = new this.modelClass();
                this._renderEdit();
                Backbone.trigger(this.componentId + '-' + 'post-log-create', {view: this});
            }
        },
        list: function(params) {
            if (params) {
                var data = params.data;
            }
            if (App.Utils.eventExists(this.componentId + '-' +'instead-log-list')) {
                Backbone.trigger(this.componentId + '-' + 'instead-log-list', {view: this, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-log-list', {view: this, data: data});
                var self = this;
				if(!this.logModelList){
                 this.logModelList = new this.listModelClass();
				}
                this.logModelList.fetch({
                    data: data,
                    success: function() {
                        self._renderList();
                        Backbone.trigger(self.componentId + '-' + 'post-log-list', {view: self});
                    },
                    error: function(mode, error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'log-list', view: self, error: error});
                    }
                });
            }
        },
        edit: function(params) {
            var id = params.id;
            var data = params.data;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-log-edit')) {
                Backbone.trigger(this.componentId + '-' + 'instead-log-edit', {view: this, id: id, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-log-edit', {view: this, id: id, data: data});
                if (this.logModelList) {
                    this.currentLogModel = this.logModelList.get(id);
                    this._renderEdit();
                    Backbone.trigger(this.componentId + '-' + 'post-log-edit', {view: this, id: id, data: data});
                } else {
                    var self = this;
                    this.currentLogModel = new this.modelClass({id: id});
                    this.currentLogModel.fetch({
                        data: data,
                        success: function() {
                            self._renderEdit();
                            Backbone.trigger(self.componentId + '-' + 'post-log-edit', {view: this, id: id, data: data});
                        },
                        error: function() {
                            Backbone.trigger(self.componentId + '-' + 'error', {event: 'log-edit', view: self, id: id, data: data, error: error});
                        }
                    });
                }
            }
        },
        destroy: function(params) {
            var id = params.id;
            var self = this;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-log-delete')) {
                Backbone.trigger(this.componentId + '-' + 'instead-log-delete', {view: this, id: id});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-log-delete', {view: this, id: id});
                var deleteModel;
                if (this.logModelList) {
                    deleteModel = this.logModelList.get(id);
                } else {
                    deleteModel = new this.modelClass({id: id});
                }
                deleteModel.destroy({
                    success: function() {
                        Backbone.trigger(self.componentId + '-' + 'post-log-delete', {view: self, model: deleteModel});
                    },
                    error: function() {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'log-delete', view: self, error: error});
                    }
                });
            }
        },
		_loadRequiredComponentsData: function(callBack) {
            var self = this;
            var listReady = _.after(2, function(){
                callBack();
            }); 
            var listDataReady = function(componentName, model){
                self[componentName] = model;
                listReady();
            };
				App.Utils.getComponentList('bodegaComponent',listDataReady);
				App.Utils.getComponentList('productoComponent',listDataReady);
        },
        save: function() {
            var self = this;
            var model = $('#' + this.componentId + '-logForm').serializeObject();
            if (App.Utils.eventExists(this.componentId + '-' +'instead-log-save')) {
                Backbone.trigger(this.componentId + '-' + 'instead-log-save', {view: this, model : model});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-log-save', {view: this, model : model});
                this.currentLogModel.set(model);
                this.currentLogModel.save({},
                        {
                            success: function(model) {
                                Backbone.trigger(self.componentId + '-' + 'post-log-save', {model: self.currentLogModel});
                            },
                            error: function(error) {
                                Backbone.trigger(self.componentId + '-' + 'error', {event: 'log-save', view: self, error: error});
                            }
                        });
            }
        },
        
        
        reaprov: function(params) {
            if (params) {
                console.log('Entra a la primera')
                var data = params.data;
            }
            if (App.Utils.eventExists(this.componentId + '-' +'instead-log-list')) {
                Backbone.trigger(this.componentId + '-' + 'instead-log-list', {view: this, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-log-list', {view: this, data: data});
                var self = this;
                
				if(!this.logModelList){
                 this.logModelList = new this.listModelClass();
				}
                this.logModelList.fetch({
                    data: data,
                    success: function() {
                        
                        var elementos = self.logModelList.models;  
                        console.log(self.logModelList);
                        self.logModelList = new App.Model.LogList();
                        console.log(self.logModelList);
                        console.log('PreForEach')
                        
                        _.each(elementos, function(d) {
                            //Se hace el cálculo del nuevo campo
                            var tipo = d.attributes.justificacion;
                            console.log(tipo);
                            if(tipo == 'Orden de reaprovisionamiento')
                            {
                                console.log('entra');
                                self.logModelList.models.push(d);
                            }
                            
                        });
                        
                        
                        
                        
                        
                        
                        
                        self._renderList();
                        Backbone.trigger(self.componentId + '-' + 'post-log-list', {view: self});
                    },
                    error: function(mode, error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'log-list', view: self, error: error});
                    }
                });
            }
        },
        
        
        
        
        _renderList: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.listTemplate({logs: self.logModelList.models, componentId: self.componentId, showEdit : self.showEdit , showDelete : self.showDelete}));
                self.$el.slideDown("fast");
            });
        },
        _renderEdit: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.editTemplate({log: self.currentLogModel, componentId: self.componentId , showEdit : self.showEdit , showDelete : self.showDelete
 
				    ,bodega: self.bodegaComponent
 
				    ,producto: self.productoComponent
 
				}));
                self.$el.slideDown("fast");
            });
        }
    });
    return App.Controller._LogController;
});