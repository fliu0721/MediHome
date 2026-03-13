<template>
  <div class="drug-form">
    <el-card>
      <template #header>
        <span>{{ isEdit ? '编辑药品' : '添加药品' }}</span>
      </template>

      <el-form 
        :model="form" 
        :rules="rules" 
        ref="formRef" 
        label-width="100px"
        style="max-width: 800px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="药品名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入药品名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类" prop="categoryId">
              <el-select v-model="form.categoryId" placeholder="选择分类" style="width: 100%">
                <el-option 
                  v-for="item in categories" 
                  :key="item.id" 
                  :label="item.name" 
                  :value="item.id" 
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="规格" prop="specification">
              <el-input v-model="form.specification" placeholder="如：0.25g*24粒" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产厂家" prop="manufacturer">
              <el-input v-model="form.manufacturer" placeholder="请输入生产厂家" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="生产日期" prop="produceDate">
              <el-date-picker 
                v-model="form.produceDate" 
                type="date" 
                placeholder="选择生产日期"
                style="width: 100%"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="有效期" prop="expiryDate">
              <el-date-picker 
                v-model="form.expiryDate" 
                type="date" 
                placeholder="选择有效期"
                style="width: 100%"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="库存数量" prop="stockQuantity">
              <el-input-number v-model="form.stockQuantity" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库存单位" prop="stockUnit">
              <el-input v-model="form.stockUnit" placeholder="如：粒、片、盒" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="存放位置" prop="location">
          <el-input v-model="form.location" placeholder="如：客厅药箱" />
        </el-form-item>

        <el-form-item label="用法用量" prop="usageDosage">
          <el-input 
            v-model="form.usageDosage" 
            type="textarea" 
            :rows="2"
            placeholder="请输入用法用量"
          />
        </el-form-item>

        <el-form-item label="适应症" prop="indication">
          <el-input 
            v-model="form.indication" 
            type="textarea" 
            :rows="2"
            placeholder="请输入适应症"
          />
        </el-form-item>

        <el-form-item label="禁忌" prop="contraindication">
          <el-input 
            v-model="form.contraindication" 
            type="textarea" 
            :rows="2"
            placeholder="请输入禁忌"
          />
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input 
            v-model="form.remark" 
            type="textarea" 
            :rows="2"
            placeholder="请输入备注"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="submitting">
            {{ isEdit ? '保存' : '添加' }}
          </el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addDrug, updateDrug, getDrugDetail } from '@/api/drug'
import { getCategoryList } from '@/api/drugCategory'

const route = useRoute()
const router = useRouter()
const formRef = ref()
const submitting = ref(false)
const categories = ref([])

const isEdit = computed(() => !!route.params.id)

const form = reactive({
  name: '',
  categoryId: null,
  specification: '',
  manufacturer: '',
  produceDate: '',
  expiryDate: '',
  stockQuantity: 0,
  stockUnit: '',
  location: '',
  usageDosage: '',
  indication: '',
  contraindication: '',
  remark: ''
})

const rules = {
  name: [{ required: true, message: '请输入药品名称', trigger: 'blur' }],
  expiryDate: [{ required: true, message: '请选择有效期', trigger: 'change' }],
  stockQuantity: [{ required: true, message: '请输入库存数量', trigger: 'blur' }]
}

const loadCategories = async () => {
  const res = await getCategoryList()
  categories.value = res
}

const loadDrugDetail = async () => {
  if (!isEdit.value) return
  const res = await getDrugDetail(route.params.id)
  Object.assign(form, res)
}

const handleSubmit = async () => {
  await formRef.value.validate()
  submitting.value = true
  try {
    if (isEdit.value) {
      await updateDrug(route.params.id, form)
      ElMessage.success('修改成功')
    } else {
      await addDrug(form)
      ElMessage.success('添加成功')
    }
    router.push('/drugs')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadCategories()
  loadDrugDetail()
})
</script>
