```js
yunzhou-admin                 
├─ public                             
│  ├─ static                          
│  │  └─ css                          
│  │     └─ loading.css               
│  ├─ favicon.ico                     
│  ├─ favicon_backup.ico              
│  └─ index.html                      
├─ src                                
│  ├─ api                             
│  │  ├─ ad.js                        
│  │  ├─ article.js                   
│  │  ├─ channel.js                   
│  │  ├─ column.js                    
│  │  ├─ enterprise.js                
│  │  ├─ gathering.js                 
│  │  ├─ label.js                     
│  │  ├─ publicKey.js                 
│  │  ├─ recruit.js                   
│  │  ├─ router.js                    
│  │  └─ user.js                      
│  ├─ assets                          
│  │  ├─ comparison                   
│  │  │  ├─ left.jpg                  
│  │  │  └─ right.jpg                 
│  │  ├─ error_images                 
│  │  │  ├─ 401.png                   
│  │  │  ├─ 404.png                   
│  │  │  └─ cloud.png                 
│  │  ├─ login_images                 
│  │  │  └─ background.jpg            
│  │  ├─ qr_logo                      
│  │  │  └─ lqr_logo.png              
│  │  ├─ ewm.png                      
│  │  ├─ ewm_vip.png                  
│  │  └─ pro.png                      
│  ├─ colorfulIcon                    
│  │  ├─ svg                          
│  │  │  ├─ alphabetical_sorting.svg  
│  │  │  └─ vab.svg                   
│  │  └─ index.js                     
│  ├─ components                      
│  │  └─ VabUpload                    
│  │     └─ index.vue                 
│  ├─ config                          
│  │  ├─ index.js                     
│  │  ├─ net.config.js                
│  │  ├─ permission.js                
│  │  ├─ setting.config.js            
│  │  ├─ settings.js                  
│  │  └─ theme.config.js              
│  ├─ layouts                         
│  │  ├─ components                   
│  │  │  ├─ Amap                      
│  │  │  │  └─ index.vue              
│  │  │  ├─ VabAd                     
│  │  │  │  └─ index.vue              
│  │  │  ├─ VabAppMain                
│  │  │  │  └─ index.vue              
│  │  │  ├─ VabAvatar                 
│  │  │  │  └─ index.vue              
│  │  │  ├─ VabBreadcrumb             
│  │  │  │  └─ index.vue              
│  │  │  ├─ VabLogo                   
│  │  │  │  └─ index.vue              
│  │  │  ├─ VabNavBar                 
│  │  │  │  └─ index.vue              
│  │  │  ├─ VabThemeBar               
│  │  │  │  └─ index.vue              
│  │  │  └─ Vditor                    
│  │  │     └─ index.vue              
│  │  ├─ EmptyLayout.vue              
│  │  ├─ export.js                    
│  │  └─ index.vue                    
│  ├─ plugins                         
│  │  ├─ element.js                   
│  │  ├─ index.js                     
│  │  ├─ support.js                   
│  │  └─ vabIcon.js                   
│  ├─ remixIcon                       
│  │  ├─ svg                          
│  │  │  ├─ qq-fill.svg               
│  │  │  └─ vuejs-fill.svg            
│  │  └─ index.js                     
│  ├─ router                          
│  │  └─ index.js                     
│  ├─ store                           
│  │  ├─ modules                      
│  │  │  ├─ errorLog.js               
│  │  │  ├─ routes.js                 
│  │  │  ├─ settings.js               
│  │  │  ├─ table.js                  
│  │  │  ├─ tabsBar.js                
│  │  │  └─ user.js                   
│  │  └─ index.js                     
│  ├─ styles                          
│  │  ├─ spinner                      
│  │  │  ├─ dots.css                  
│  │  │  ├─ gauge.css                 
│  │  │  ├─ inner-circles.css         
│  │  │  └─ plus.css                  
│  │  ├─ themes                       
│  │  │  ├─ dark.scss                 
│  │  │  ├─ default.scss              
│  │  │  ├─ glory.scss                
│  │  │  └─ green.scss                
│  │  ├─ element-variables.scss       
│  │  ├─ loading.scss                 
│  │  ├─ normalize.scss               
│  │  ├─ transition.scss              
│  │  ├─ vab.scss                     
│  │  └─ variables.scss               
│  ├─ utils                           
│  │  ├─ accessToken.js               
│  │  ├─ encrypt.js                   
│  │  ├─ errorLog.js                  
│  │  ├─ handleRoutes.js              
│  │  ├─ index.js                     
│  │  ├─ pageTitle.js                 
│  │  ├─ permission.js                
│  │  ├─ request.js                   
│  │  ├─ static.js                    
│  │  ├─ vab.js                       
│  │  └─ validate.js                  
│  ├─ views                           
│  │  ├─ index                        
│  │  │  └─ index.vue                 
│  │  ├─ login                        
│  │  │  └─ index.vue                 
│  │  ├─ project                      
│  │  │  ├─ actuator                  
│  │  │  │  ├─ bootserver             
│  │  │  │  │  └─ index.vue           
│  │  │  │  ├─ consul                 
│  │  │  │  │  └─ index.vue           
│  │  │  │  └─ druid                  
│  │  │  │     └─ index.vue           
│  │  │  ├─ article                   
│  │  │  │  ├─ components             
│  │  │  │  │  └─ ArticleEdit.vue     
│  │  │  │  └─ index.vue              
│  │  │  ├─ channel                   
│  │  │  │  ├─ components             
│  │  │  │  │  └─ ChannelEdit.vue     
│  │  │  │  └─ index.vue              
│  │  │  ├─ column                    
│  │  │  │  ├─ components             
│  │  │  │  │  └─ ColumnEdit.vue      
│  │  │  │  └─ index.vue              
│  │  │  ├─ enterprise                
│  │  │  │  ├─ components             
│  │  │  │  │  └─ EnterpriseEdit.vue  
│  │  │  │  └─ index.vue              
│  │  │  ├─ gathering                 
│  │  │  │  ├─ components             
│  │  │  │  │  └─ GatheringEdit.vue   
│  │  │  │  └─ index.vue              
│  │  │  ├─ label                     
│  │  │  │  ├─ components             
│  │  │  │  │  └─ LabelEdit.vue       
│  │  │  │  └─ index.vue              
│  │  │  └─ recruit                   
│  │  │     ├─ components             
│  │  │     │  └─ RecruitEdit.vue     
│  │  │     └─ index.vue              
│  │  ├─ register                     
│  │  │  └─ index.vue                 
│  │  ├─ 401.vue                      
│  │  └─ 404.vue                      
│  ├─ App.vue                         
│  └─ main.js                         
├─ babel.config.js                    
├─ deploy.sh                          
├─ LICENSE                            
├─ license.md                         
├─ package-lock.json                  
├─ package.json                       
├─ plopfile.js                        
├─ prettier.config.js                 
├─ push.sh                            
├─ README-DCLOUD.md                   
├─ README.md                          
├─ vue.config.js                      
└─ webstorm.config.js                 

```