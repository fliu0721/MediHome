<template>
  <div class="categories">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>药品分类</span>
          <el-button type="primary" @click="showAddDialog">
            <el-icon><Plus /></el-icon> 添加分类
          </el-button>
        </div>
      </template>

      <el-table :data="categoryList" v-loading="loading" border>
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="sortOrder" label="排序" width="100" />
        <el-table-column label="类型" width="120">
          <template #default="{ row }">
            <el-tag v-if="row.userId === 0" type="info">系统默认</el-tag>
            <el-tag v-else type="success">自定义</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button 
              link 
              type="primary" 
              @click="handleEdit(row)"
              v-if="row.userId !== 0"
            >
              编辑
            </el-button>
            <el-button 
              link 
              type="danger" 
              @click="handleDelete(row)"
              v-if="row.userId !== 0"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="400px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="排序号" prop="sortOrder">
          <el-input-number v-model="form.sortOrder" :min="0" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getCategoryList, addCategory, updateCategory, deleteCategory } from '@/api/drugCategory'

const loading = ref(false)
const categoryList = ref([])
const dialogVisible = ref(false)
const submitting = ref(false)
const isEdit = ref(false)
const currentId = ref(null)
const formRef = ref()

const dialogTitle = computed(() => isEdit.value ? '编辑分类' : '添加分类')

const form = reactive({
  name: '',
  sortOrder: 0
})

const rules = {
  name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCategoryList()
    categoryList.value = res
  } finally {
    loading.value = false
  }
}

const showAddDialog = () => {
  isEdit.value = false
  currentId.value = null
  form.name = ''
  form.sortOrder = 0
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  currentId.value = row.id
  form.name = row.name
  form.sortOrder = row.sortOrder
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  submitting.value = true
  try {
    if (isEdit.value) {
      await updateCategory(currentId.value, form)
      ElMessage.success('修改成功')
    } else {
      await addCategory(form)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    loadData()
  } finally {
    submitting.value = false
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该分类吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteCategory(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

loadData()
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
