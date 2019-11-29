<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="接口名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'interfaceName', validatorRules.interfaceName]" placeholder="请输入接口名称"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="业务类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['interfaceType']" :trigger-change="true" dictCode="service_type" placeholder="请选择业务类型"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="传输类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['transferType']" :trigger-change="true" dictCode="transfer_tpye" placeholder="请选择传输类型"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="是否生效" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['beactive']" :trigger-change="true" dictCode="status_code" placeholder="请选择是否生效"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="访问地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入访问地址"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="表名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'tablename', validatorRules.tablename]" placeholder="请输入表名"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="接口用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'interfaceUsername', validatorRules.interfaceUsername]" placeholder="请输入接口用户名"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="接口密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'interfacePassword', validatorRules.interfacePassword]" placeholder="请输入接口密码"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="定时任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'taskParam', validatorRules.taskParam]" placeholder="请输入定时任务"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="报文格式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['pomParam']" :trigger-change="true" dictCode="pom_param" placeholder="请选择报文格式"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="24">
            <a-form-item label="json格式" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['jsonDetail']" rows="4" placeholder="请输入json格式"/>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="字段对应表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="tableOptionTable.loading"
            :columns="tableOptionTable.columns"
            :dataSource="tableOptionTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'InterfaceOptionModal',
    mixins: [JEditableTableMixin],
    components: {
      JDictSelectTag,
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          interfaceName:{},
          interfaceType:{},
          transferType:{},
          beactive:{},
          address:{},
          tablename:{},
          interfaceUsername:{},
          interfacePassword:{},
          taskParam:{},
          pomParam:{},
          jsonDetail:{},
        },
        refKeys: ['tableOption', ],
        tableKeys:['tableOption', ],
        activeKey: 'tableOption',
        // 字段对应表
        tableOptionTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '上游字段',
              key: 'upColumnName',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '下游子段',
              key: 'downCloumnName',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '父字段',
              key: 'parentColumnName',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '标识',
              key: 'flag',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '子字段',
              key: 'childColumnName',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '默认值',
              key: 'defaultValue',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '字段类型',
              key: 'typeName',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '字段长度',
              key: 'coolumnSize',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '是否为空',
              key: 'isEmpty',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '表名',
              key: 'tableName',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '备注',
              key: 'remark',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '创建人',
              key: 'createBy',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '创建日期',
              key: 'createTime',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '更新人',
              key: 'updateBy',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '更新日期',
              key: 'updateTime',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
          ]
        },
        url: {
          add: "/interfaceOption/interfaceOption/add",
          edit: "/interfaceOption/interfaceOption/edit",
          tableOption: {
            list: '/interfaceOption/interfaceOption/queryTableOptionByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'interfaceName','interfaceType','transferType','beactive','address','tablename','interfaceUsername','interfacePassword','taskParam','pomParam','jsonDetail')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.tableOption.list, params, this.tableOptionTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          tableOptionList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      }
      
      
    }
  }
</script>

<style scoped>
</style>