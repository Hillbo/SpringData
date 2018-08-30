package com.sdata.controller;

import com.sdata.mapper.StudentDao;
import com.sdata.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/studentService")
@Component
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    @POST
    @Path("/insertStudent")
    @Transactional
    public void addStudents(List<Student> students) {
        try {
            studentDao.saveAll(students);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/deleteStudent")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteStudentById(@QueryParam("id") String id) {
        try {
            studentDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Success!";
    }

    @GET
    @Path("/queryByStudentId")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Student queryByStudentId(@QueryParam("id") String id) {
        Student student = null;
        try {
            Student ss = new Student("111",null,null,null);
            student = studentDao.findById(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @GET
    @Path("/queryStudentById")
    @Produces(MediaType.APPLICATION_JSON)
    public Student queryStudentById(@QueryParam("id") String id) {
        Student student = null;
        try {
            Student ss = new Student();
            ss.setId(id);
            Example<Student> example = Example.of(ss);
            Student asd = studentDao.findOne(example).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @GET
    @Path("/queryStudentsPage")
    @Produces(MediaType.TEXT_PLAIN)
    public String queryStudentsPage() {
        try {
            // page 从0开始，不是从1开始
            Sort.Order order = Sort.Order.asc("id");
            Sort sort = Sort.by(order);
            Pageable pageable = PageRequest.of(0,3, sort);
            Page<Student> page = studentDao.findAll(pageable);
            System.out.println("查询的总页数：" + page.getTotalPages());
            System.out.println("查询的总记录数：" + page.getTotalElements());
            System.out.println("查询的当前第几页：" + page.getNumber() + 1);
            System.out.println("查询的当前页面的集合：" + page.getContent());
            System.out.println("查询的当前页面的记录数：" + page.getNumberOfElements());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Success!";
    }

    /**
     * 1、分页
     * 2、排序
     * 3、查询条件: age>12
     */
    @GET
    @Path("/queryByCondition")
    @Produces(MediaType.TEXT_PLAIN)
    public String queryByCondition() {
        try {
            // page 从0开始，不是从1开始
            Sort.Order order = Sort.Order.asc("id");
            Sort sort = Sort.by(order);
            Pageable pageable = PageRequest.of(1,3, sort);

            Specification<Student> specification = new Specification<Student>() {
                /**
                 *
                 * @param root 根对象,即要查询的类型
                 * @param criteriaQuery 添加查询条件
                 * @param criteriaBuilder 构建Predicate
                 */
                @Override
                public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    // path相当于路径root-->Student-->age
                    javax.persistence.criteria.Path path = root.get("age");
                    return criteriaBuilder.gt(path, 12);
                }
            };
            Page<Student> page = studentDao.findAll(specification, pageable);

            System.out.println("查询的总页数：" + page.getTotalPages());
            System.out.println("查询的总记录数：" + page.getTotalElements());
            System.out.println("查询的当前第几页：" + page.getNumber() + 1);
            System.out.println("查询的当前页面的集合：" + page.getContent());
            System.out.println("查询的当前页面的记录数：" + page.getNumberOfElements());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Success!";
    }
}

