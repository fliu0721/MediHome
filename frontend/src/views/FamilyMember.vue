<template>
  <div class="family-member">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>家庭成员</span>
          <el-button type="primary" @click="showAddDialog">
            <el-icon><Plus /></el-icon> 添加成员
          </el-button>
        </div>
      </template>

      <el-table :data="memberList" v-loading="loading" border>
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="age" label="年龄" width="80" />
        <el-table-column prop="gender" label="性别" width="80">
          <template #default="{ row }">
            {{ row.gender === 1 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="allergyHistory" label="过敏史" show-overflow-tooltip />
        <el-table-column prop="commonDrugs" label="常用药品" show-overflow-tooltip />
        <el-table-column prop="isDefault" label="默认" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.isDefault === 1" type="success">是</el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="primary" @click="handleSetDefault(row)" v-if="row.isDefault !== 1">
              设为默认
            </el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="0" :max="150" style="width: 100%" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="过敏史" prop="allergyHistory">
          <el-input 
            v-model="form.allergyHistory" 
            type="textarea" 
            :rows="2"
            placeholder="请输入过敏史"
          />
        </el-form-item>
        <el-form-item label="常用药品" prop="commonDrugs">
          <el-input 
            v-model="form.commonDrugs" 
            type="textarea" 
            :rows="2"
            placeholder="请输入常用药品"
          />
        </el-form-item>
        <el-form-item label="默认成员" prop="isDefault">
          <el-switch v-model="form.isDefault" :active-value="1" :inactive-value="0" />
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
import { 
  getFamilyMemberList, 
  addFamilyMember, 
  updateFamilyMember, 
  deleteFamilyMember,
  setDefaultMember 
} from '@/api/familyMember'

const loading = ref(false)
const memberList = ref([])
const dialogVisible = ref(false)
const submitting = ref(false)
const isEdit = ref(false)
const currentId = ref(null)
const formRef = ref()

const dialogTitle = computed(() => isEdit.value ? '编辑成员' : '添加成员')

const form = reactive({
  name: '',
  age: null,
  gender: 1,
  allergyHistory: '',
  commonDrugs: '',
  isDefault: 0
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getFamilyMemberList()
    memberList.value = res
  } finally {
    loading.value = false
  }
}

const showAddDialog = () => {
  isEdit.value = false
  currentId.value = null
  Object.assign(form, {
    name: '',
    age: null,
    gender: 1,
    allergyHistory: '',
    commonDrugs: '',
    isDefault: 0
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  currentId.value = row.id
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  submitting.value = true
  try {
    if (isEdit.value) {
      await updateFamilyMember(currentId.value, form)
      ElMessage.success('修改成功')
    } else {
      await addFamilyMember(form)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    loadData()
  } finally {
    submitting.value = false
  }
}

const handleSetDefault = async (row) => {
  await setDefaultMember(row.id)
  ElMessage.success('设置成功')
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该成员吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteFamilyMember(row.id)
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
