<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="接口名称">
              <a-input placeholder="请输入接口名称" v-model="queryParam.interfaceName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="业务类型">
              <j-dict-select-tag placeholder="请选择业务类型" v-model="queryParam.interfaceType" dictCode="service_type"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="传输类型">
                <j-dict-select-tag placeholder="请选择传输类型" v-model="queryParam.transferType" dictCode="transfer_tpye"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="是否生效">
                <j-dict-select-tag placeholder="请选择是否生效" v-model="queryParam.beactive" dictCode="status_code"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="表名">
                <a-input placeholder="请输入表名" v-model="queryParam.tablename"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('接口配置')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <interfaceOption-modal ref="modalForm" @ok="modalFormOk"></interfaceOption-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import InterfaceOptionModal from './modules/InterfaceOptionModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  export default {
    name: "InterfaceOptionList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      InterfaceOptionModal
    },
    data () {
      return {
        description: '接口配置管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'接口名称',
            align:"center",
            dataIndex: 'interfaceName'
          },
          {
            title:'业务类型',
            align:"center",
            dataIndex: 'interfaceType',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['interfaceType'], text+"")
              }
            }
          },
          {
            title:'传输类型',
            align:"center",
            dataIndex: 'transferType',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['transferType'], text+"")
              }
            }
          },
          {
            title:'是否生效',
            align:"center",
            dataIndex: 'beactive',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['beactive'], text+"")
              }
            }
          },
          {
            title:'访问地址',
            align:"center",
            dataIndex: 'address'
          },
          {
            title:'表名',
            align:"center",
            dataIndex: 'tablename'
          },
          {
            title:'接口用户名',
            align:"center",
            dataIndex: 'interfaceUsername'
          },
          {
            title:'接口密码',
            align:"center",
            dataIndex: 'interfacePassword'
          },
          {
            title:'定时任务',
            align:"center",
            dataIndex: 'taskParam'
          },
          {
            title:'报文格式',
            align:"center",
            dataIndex: 'pomParam',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['pomParam'], text+"")
              }
            }
          },
          {
            title:'json格式',
            align:"center",
            dataIndex: 'jsonDetail'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/interfaceOption/interfaceOption/list",
          delete: "/interfaceOption/interfaceOption/delete",
          deleteBatch: "/interfaceOption/interfaceOption/deleteBatch",
          exportXlsUrl: "/interfaceOption/interfaceOption/exportXls",
          importExcelUrl: "interfaceOption/interfaceOption/importExcel",
        },
        dictOptions:{
         interfaceType:[],
         transferType:[],
         beactive:[],
         pomParam:[],
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('service_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'interfaceType', res.result)
          }
        })
        initDictOptions('transfer_tpye').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'transferType', res.result)
          }
        })
        initDictOptions('status_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'beactive', res.result)
          }
        })
        initDictOptions('pom_param').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'pomParam', res.result)
          }
        })
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>